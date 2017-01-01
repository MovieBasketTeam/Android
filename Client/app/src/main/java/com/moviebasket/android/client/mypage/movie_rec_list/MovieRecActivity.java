package com.moviebasket.android.client.mypage.movie_rec_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.MBService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRecActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<RecDatas> mDatas = new ArrayList<RecDatas>();

    private MBService mbService;
    private boolean isResponseSuccess;
    private static final String FAILURE = "view failed";


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

        String token = ApplicationController.getInstance().getPreferences();

        Call<RecResultParent> getRecommendResult = mbService.getRecommendResult(token);
        getRecommendResult.enqueue(new Callback<RecResultParent>() {
            @Override
            public void onResponse(Call<RecResultParent> call, Response<RecResultParent> response) {
                Log.i("NetConfirm", "onResponse: 들어옴");

                RecResultParent recResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("recommendMovie Test", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                    isResponseSuccess = recResult.result.result.get(0).message==null ? true : false;
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
        adapter = new RecAdapter(mDatas, recylerClickListener);
        recyclerView.setAdapter(adapter);

    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법
            String MovieTitle = mDatas.get(position).movie_title;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            //Toast.makeText(MovieSearchActivity.this, position+"번째 리사이클러뷰 항목 클릭!", Toast.LENGTH_SHORT).show();
            Toast.makeText(MovieRecActivity.this, MovieTitle, Toast.LENGTH_SHORT).show();
        }
    };
}