package com.moviebasket.android.client.mypage.movie_rec_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.clickable.OneClickable;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.movie_detail.MovieDetailDialog;
import com.moviebasket.android.client.network.MBService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRecActivity extends AppCompatActivity implements OneClickable {
    RecyclerView recyclerView;
    ArrayList<RecDatas> mDatas = new ArrayList<RecDatas>();

    ImageView heart;
    ImageView book_mark;
    String token;

    private MBService mbService;
    private boolean isResponseSuccess;
    private boolean isHeartSuccess;
    private static final String FAILURE = "view failed";
    private MovieDetailDialog detailDialog;
    int position;

    LinearLayoutManager mLayoutManager;
    RecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec);

        /**
         * 1. recyclerview 초기화
         */
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerview);
        mbService = ApplicationController.getInstance().getMbService();
        heart = (ImageView) findViewById(R.id.heart);
        book_mark = (ImageView) findViewById(R.id.heart);
        token = ApplicationController.getInstance().getPreferences();
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        /**
         * 2. recyclerview에 보여줄 data
         */
        mDatas = new ArrayList<RecDatas>();

        Call<RecResultParent> getRecommendResult = mbService.getRecommendResult(token);
        getRecommendResult.enqueue(new Callback<RecResultParent>() {
            @Override
            public void onResponse(Call<RecResultParent> call, Response<RecResultParent> response) {
                Log.i("NetConfirm", "onResponse: 들어옴");

                RecResultParent recResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("recommendMovie Test", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                    isResponseSuccess = recResult.result.result != null ? true : false;  //여기 문제......심각//
                    Log.i("recommendMovie Test", "응답 결과 : " + isResponseSuccess);
                }
                if (isResponseSuccess) {
                    mDatas.addAll(recResult.result.result);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RecResultParent> call, Throwable t) {
                Log.i("NetConfirm", "onFailure: 들어옴");

                Toast.makeText(MovieRecActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.i("recommendMovie Test", "요청메시지:" + call.toString());
            }
        });


        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        adapter = new RecAdapter(mDatas, recylerClickListener, this);
        recyclerView.setAdapter(adapter);

    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법
            String MovieTitle = mDatas.get(position).movie_title;

            //영화 상세보기 다이얼로그를 띄워주기 위함
            detailDialog = new MovieDetailDialog(MovieRecActivity.this);
            detailDialog.show();
        }
    };

    @Override
    public void processOneMethodAtPosition(final int position) {
        Call<HeartResult> getHeartReasult = mbService.getHeartResult(mDatas.get(position).movie_id, mDatas.get(position).is_liked, token);
        getHeartReasult.enqueue(new Callback<HeartResult>() {
            @Override
            public void onResponse(Call<HeartResult> call, Response<HeartResult> response) {
                Log.i("NetConfirm", "onResponse: 하트에들어옴");
                HeartResult heartResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("Heart", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                    Log.i("Heart", "응답 결과 : " + heartResult.result.message);
                    isHeartSuccess = heartResult.result.message != null ? true : false;
                    Log.i("Heart", "응답 결과 : " + isHeartSuccess);
                    Log.i("Heart", "포지션 : " + mDatas.get(position));
                    Log.i("Heart", "무비아이디 : " + mDatas.get(position).movie_id);
                }
                if (isHeartSuccess) {
                    mDatas.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HeartResult> call, Throwable t) {
                Log.i("NetConfirm", "onFailure: 들어옴" + call.toString());
                Toast.makeText(MovieRecActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}