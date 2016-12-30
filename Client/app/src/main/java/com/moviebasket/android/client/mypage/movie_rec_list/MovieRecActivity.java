package com.moviebasket.android.client.mypage.movie_rec_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.moviebasket.android.client.R;

import java.util.ArrayList;

public class MovieRecActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<RecDatas> mDatas = new ArrayList<RecDatas>();

    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec);

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
         * 2. recyclerview에 보여줄 data
         */
        mDatas = new ArrayList<RecDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!
        //7,8번째 파라미터는 if문으로 값이 0,1 일때 각각 다른이미지가 뜨도록 해야함!!
        mDatas.add(new RecDatas(R.drawable.back, "owner", "1004", "라라랜드", "이필주", "미국", R.drawable.back, R.drawable.back));

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        RecAdapter adapter = new RecAdapter(mDatas);

        recyclerView.setAdapter(adapter);

    }
}
