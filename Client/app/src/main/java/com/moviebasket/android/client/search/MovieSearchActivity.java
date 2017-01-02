package com.moviebasket.android.client.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.network.NaverService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchActivity extends AppCompatActivity {



    EditText searchMovieName;
    ImageButton searchMovieBtn;
    ImageView image;

    NaverService naverService;
    MBService mbService;

    RecyclerView recyclerView;
    ArrayList<MoviesDatas> mDatas = new ArrayList<MoviesDatas>();
    LinearLayoutManager mLayoutManager;


    MovieDataResult result;
    ArrayList<MovieDetail> movieDetails;
    private ProgressDialog mProgressDialog;
    String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        naverService = ApplicationController.getInstance().getNaverService();
        mbService = ApplicationController.getInstance().getMbService();

        searchMovieName = (EditText)findViewById(R.id.searchMovieName);
        searchMovieBtn = (ImageButton)findViewById(R.id.searchMovieBtn);

        /**
         * 1. recyclerview 초기화
         */
        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerview);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // LayoutManager 초기화
        // layoutManager 설정

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        final MoviesAdapter adapter = new MoviesAdapter(mDatas, recylerClickListener);
        recyclerView.setAdapter(adapter);

        mProgressDialog = new ProgressDialog(MovieSearchActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("검색중..");
        mProgressDialog.setIndeterminate(true);

        searchMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //네이버 네트워킹 테스트
                mProgressDialog.show();
                if ( searchMovieName.getText().toString() != null && searchMovieName.getText().toString().length() != 0 ) {
                    query = searchMovieName.getText().toString();
                } else {
                    query = "movie";
                }
                /**
                 * 2. recyclerview에 보여줄 data
                 */
                TextView search_korean = (TextView)findViewById(R.id.search_korean);
                ImageView search_nosearch = (ImageView)findViewById(R.id.search_nosearch);
                search_korean.setText("");
                search_nosearch.setImageResource(0);

                Call<MovieDataResult> getMovieData = naverService.getMovieDataResult(query);
                getMovieData.enqueue(new Callback<MovieDataResult>() {
                    @Override
                    public void onResponse(Call<MovieDataResult> call, Response<MovieDataResult> response) {
                        if (response.isSuccessful()) {
                            result = response.body();
                            movieDetails = result.items;
                            mDatas.clear();
                            for (int i = 0; i < movieDetails.size(); i++) {
//                                Log.i("태그제거전", movieDetails.get(i).title);
//                                Log.i("제거이후", RemoveHTMLTag(movieDetails.get(i).title));
                                mDatas.add(new MoviesDatas(movieDetails.get(i).image,
                                        RemoveHTMLTag(movieDetails.get(i).title),
                                        movieDetails.get(i).pubDate,
                                        movieDetails.get(i).director,
                                        "국가",
                                        movieDetails.get(i).userRating));


                            }

                            mProgressDialog.dismiss();
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDataResult> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildLayoutPosition(v);

            //서버에 요청해야할 매개변수들
            String token = ApplicationController.getInstance().getPreferences();
            int basket_id = Integer.parseInt(getIntent().getExtras().getString("basket_id"));
            String movie_title = RemoveHTMLTag(movieDetails.get(position).title);
            String movie_image = movieDetails.get(position).image;
            int movie_pub_date = Integer.parseInt(movieDetails.get(position).pubDate);
            String movie_director = RemoveSpecitialCharacter(movieDetails.get(position).director);
            String movie_user_rating = movieDetails.get(position).userRating;
            String movie_link = movieDetails.get(position).link;


            /* SpecificBasket에 보여줄 것
            String movie_image;
            String movie_adder;
            String movie_title;
            int movie_pub_date;
            String movie_director;
            int movie_like;
            int is_liked;
            int is_cart;
            */



            Call<VerifyMovieAddResult> verifyMovieAddResultCall =
                    mbService.verifyMovieAddResult(
                            token, basket_id, movie_title, movie_image, movie_pub_date, movie_director, movie_user_rating, movie_link
                    );
            verifyMovieAddResultCall.enqueue(new Callback<VerifyMovieAddResult>() {
                @Override
                public void onResponse(Call<VerifyMovieAddResult> call, Response<VerifyMovieAddResult> response) {
                    //추가 성공했을 때
                    VerifyMovieAddResult result = response.body();
                    if(result.result.message.equals("movie add success")){
                        Intent data = new Intent();
                        //추가된 영화를 보내준다.
                        // data.putExtra("addedMovie", );
                        setResult(RESULT_OK, data);
                    }else{
                        Toast.makeText(MovieSearchActivity.this, "바스켓에 영화를 추가하는데 실패했습니다", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<VerifyMovieAddResult> call, Throwable t) {
                    //통신 실패했을 때
                    Toast.makeText(MovieSearchActivity.this, "서버와 연결을 실패하였습니다", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }
            });

        }
    };

    //태그제거 메서드
    public String RemoveHTMLTag(String changeStr){
        if( changeStr != null && !changeStr.equals("") ) {
            changeStr = changeStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        } else {
            changeStr = "";
        }
        return changeStr;
    }

    //특수문자 제거
    public String RemoveSpecitialCharacter(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
        str =str.replaceAll(match, " ");
        return str;
    }
}