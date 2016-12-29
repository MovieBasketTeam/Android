package com.moviebasket.android.client.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.NaverService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*public class MovieSearchActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    ImageView image;
    ImageView circularImage;

    NaverService naverService;

    MovieDataResult result;
    ArrayList<MovieDetail> movieDetails;
    private ProgressDialog mProgressDialog;
    String query = "해리포터";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        naverService = ApplicationController.getInstance().getNaverService();

        textView = (TextView)findViewById(R.id.test_textView);
        button = (Button)findViewById(R.id.test_button);
        image = (ImageView)findViewById(R.id.test_imageView);
        circularImage = (ImageView)findViewById(R.id.test_circularImageView);

        mProgressDialog = new ProgressDialog(MovieSearchActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("등록 중...");
        mProgressDialog.setIndeterminate(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //네이버 네트워킹 테스트
                mProgressDialog.show();
                Call<MovieDataResult> getMovieData =
                        naverService.getMovieDataResult(query);
                getMovieData.enqueue(new Callback<MovieDataResult>() {
                    @Override
                    public void onResponse(Call<MovieDataResult> call, Response<MovieDataResult> response) {
                        if (response.isSuccessful()) {
                            result = response.body();
                            movieDetails = result.items;
                            textView.setText(movieDetails.get(0).image+"\n"
                                    +movieDetails.get(0).title+"\n"
                            +movieDetails.get(0).actor+"\n"
                            +movieDetails.get(0).director+"\n"
                            +movieDetails.get(0).pubDate+"\n"
                            +movieDetails.get(0).userRating+"\n"
                            +movieDetails.get(0).link);

                            String imgUrl = movieDetails.get(0).image;


                            //glide 테스트
                            Glide.with(MovieSearchActivity.this).load(imgUrl).thumbnail(0.1f).into(image);
                            Glide.with(MovieSearchActivity.this).load(imgUrl).into(circularImage);

                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDataResult> call, Throwable t) {

                    }
                });

            }
        });
    }
}*/

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
    String query = "movie";


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
                if(searchMovieName.getText().toString() != null && searchMovieName.getText().toString().length() != 0) {
                    query = searchMovieName.getText().toString();
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
                                mDatas.add(new MoviesDatas(movieDetails.get(i).image,
                                        movieDetails.get(i).title,
                                        movieDetails.get(i).pubDate,
                                        movieDetails.get(i).director,
                                        "나라",
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
}
