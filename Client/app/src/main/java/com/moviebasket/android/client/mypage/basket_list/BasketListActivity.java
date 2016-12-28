package com.moviebasket.android.client.mypage.basket_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.moviebasket.android.client.R;

import java.util.ArrayList;

public class BasketListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BasketListDatas> mDatas = new ArrayList<BasketListDatas>();

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_list);

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
        mDatas = new ArrayList<BasketListDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!
        mDatas.add(new BasketListDatas(R.drawable.basket_img, R.drawable.text_image, "이필주", R.drawable.down_btn, "1,882"));

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        BasketListAdapter adapter = new BasketListAdapter(mDatas);

        recyclerView.setAdapter(adapter);
    }
    }

