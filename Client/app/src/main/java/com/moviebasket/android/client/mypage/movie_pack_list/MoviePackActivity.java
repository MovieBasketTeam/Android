
package com.moviebasket.android.client.mypage.movie_pack_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.clickable.TwoClickable;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.movie_detail.MovieDetailDialog;
import com.moviebasket.android.client.mypage.movie_rec_list.HeartResult;
import com.moviebasket.android.client.network.MBService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviePackActivity extends AppCompatActivity implements TwoClickable {
    RecyclerView recyclerView;
    ArrayList<PackDetail> packdetail;
    LinearLayoutManager mLayoutManager;
    private MBService mbService;
    private boolean isdeletetSuccess;
    private boolean isHeartSuccess;

    PackResultResult result;
    PackAdapter adapter;

    private MovieDetailDialog detailDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pack);

        mbService = ApplicationController.getInstance().getMbService();

        /**
         * 1. recyclerview 초기화
         */
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerview);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);
        packdetail = new ArrayList<>();

        adapter = new PackAdapter(packdetail, recylerClickListener, this);
        recyclerView.setAdapter(adapter);

        // LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        /**
         * 2. recyclerview에 보여줄 data
         */
        String token = ApplicationController.getInstance().getPreferences();
        if (!token.equals("")) {
            Call<PackResultResult> getMovieData = mbService.getMoviePackResult(token);
            getMovieData.enqueue(new Callback<PackResultResult>() {
                @Override
                public void onResponse(Call<PackResultResult> call, Response<PackResultResult> response) {
                    if (response.isSuccessful()) {
                        result = response.body();

                        packdetail.addAll(result.result.result);
                        Log.i("NetConfirm", "PackDetails 들어온 데이터들 : " + packdetail.toString());

                        /**
                         * 3. Adapter 생성 후 recyclerview에 지정
                         */

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PackResultResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "로그인을 해주세요.", Toast.LENGTH_SHORT);
        }
        //
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildLayoutPosition(v);

            String movie_title = packdetail.get(position).movie_title;
            String movie_link = packdetail.get(position).movie_link;
            String movie_image = packdetail.get(position).movie_image;
            String movie_pubDate = String.valueOf(packdetail.get(position).movie_pub_date);
            String movie_director = packdetail.get(position).movie_director;
           // String movie_actor = packdetail.get(position).;
            String movie_userRating;
            String movie_summary;

            //영화 상세보기 다이얼로그를 띄워주기 위함

            //detailDialog = new MovieDetailDialog(MoviePackActivity.this, );
            detailDialog.show();
        }
    };

    //휴지통  //휴지통으로 이미지 바꾸어야한다.....
    public void processOneMethodAtPosition(final int position) {
        String token = ApplicationController.getInstance().getPreferences();
        Call<BasketListDataDeleteResult> getBasketListDataDeleteResult = mbService.getBasketListDataDeleteResult(token, packdetail.get(position).movie_id);
        getBasketListDataDeleteResult.enqueue(new Callback<BasketListDataDeleteResult>() {
            @Override
            public void onResponse(Call<BasketListDataDeleteResult> call, Response<BasketListDataDeleteResult> response) {
                Log.i("NetConfirm", "onResponse: 하트에들어옴");
                BasketListDataDeleteResult basketDeleteResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("Delete", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());

                    isdeletetSuccess = basketDeleteResult.result.message != null ? true : false;
                }
                if (isdeletetSuccess) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BasketListDataDeleteResult> call, Throwable t) {
                Log.i("NetConfirm", "onFailure: 들어옴" + call.toString());
                Toast.makeText(MoviePackActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }



    //하트눌렀을때
    public void processTwoMethodAtPosition(final int position) {
        String token = ApplicationController.getInstance().getPreferences();
        Call<HeartResult> getHeartReasult = mbService.getHeartResult(packdetail.get(position).movie_id, packdetail.get(position).is_liked, token);
        getHeartReasult.enqueue(new Callback<HeartResult>() {
            @Override
            public void onResponse(Call<HeartResult> call, Response<HeartResult> response) {
                Log.i("NetConfirm", "onResponse: 하트에들어옴");
                HeartResult heartResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("Heart", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                    Log.i("Heart", "응답 결과 : " + heartResult.result.message);
                    isHeartSuccess = heartResult.result.message != null ? true : false;

                }
                if (isHeartSuccess) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HeartResult> call, Throwable t) {
                Log.i("NetConfirm", "onFailure: 들어옴" + call.toString());
                Toast.makeText(MoviePackActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

