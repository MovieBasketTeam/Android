package com.moviebasket.android.client.mypage.movie_pack_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.moviebasket.android.client.R;

import java.util.ArrayList;

public class MoviePackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PackDatas> mDatas = new ArrayList<PackDatas>();

    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pack);

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
        mDatas = new ArrayList<PackDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!
        mDatas.add(new PackDatas(R.drawable.back, "owner", "1004", "라라랜드", "이필주", "미국"));
        mDatas.add(new PackDatas(R.drawable.back, "이충민", "888", "미스패래그린과 아이들", "마이클", "미국"));
        mDatas.add(new PackDatas(R.drawable.back, "by.이윤서", "9052", "고구마인생", "봉준호", "한국"));
        mDatas.add(new PackDatas(R.drawable.back, "김정은", "2017", "핵개발", "김정일", "북한"));


        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        PackAdapter adapter = new PackAdapter(mDatas, recylerClickListener);

        recyclerView.setAdapter(adapter);
    }
    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

            String title = mDatas.get(position).title;
            String owner = mDatas.get(position).owner;
            String likecount = mDatas.get(position).likecount;
            String directer = mDatas.get(position).directer;
            String country = mDatas.get(position).country;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(MoviePackActivity.this, position+"번째 리사이클러뷰 항목 클릭!"+title+"/"+directer+"/"+country, Toast.LENGTH_SHORT).show();
        }
    };




}