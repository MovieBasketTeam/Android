package com.moviebasket.android.client.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.NaverService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchActivity extends AppCompatActivity {

    TextView textView;
    Button button;

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
                            textView.setText(movieDetails.get(0).image+" - "+movieDetails.get(0).title);
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
}
