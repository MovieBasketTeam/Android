package com.moviebasket.android.client.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
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
                mDatas = new ArrayList<MoviesDatas>();

                Call<MovieDataResult> getMovieData = naverService.getMovieDataResult(query);
                getMovieData.enqueue(new Callback<MovieDataResult>() {
                    @Override
                    public void onResponse(Call<MovieDataResult> call, Response<MovieDataResult> response) {
                        if (response.isSuccessful()) {
                            result = response.body();
                            movieDetails = result.items;
                            for(int i = 0 ; i < movieDetails.size() ; i++) {

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

                            /**
                             * 3. Adapter 생성 후 recyclerview에 지정
                             */
                            MoviesAdapter adapter = new MoviesAdapter(mDatas);
                            recyclerView.setAdapter(adapter);
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

    //태그제거 메서드
    public String RemoveHTMLTag(String changeStr){
        if( changeStr != null && !changeStr.equals("") ) {
            changeStr = changeStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        } else {
            changeStr = "";
        }
        return changeStr;
    }
}
