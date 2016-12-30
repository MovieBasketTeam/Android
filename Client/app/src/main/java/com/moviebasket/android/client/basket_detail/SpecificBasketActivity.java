package com.moviebasket.android.client.basket_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.search.MovieSearchActivity;

import java.util.ArrayList;

public class SpecificBasketActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_MOVIE_SEARCH = 1000;

    Button btn_add_movie;

    RecyclerView recyclerView;
    ArrayList<DetailDatas> mDatas = new ArrayList<DetailDatas>();

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_basket);

        btn_add_movie = (Button)findViewById(R.id.btn_add_movie_specific);
        btn_add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieSearchIntent = new Intent(SpecificBasketActivity.this, MovieSearchActivity.class);
                startActivityForResult(movieSearchIntent, REQUEST_CODE_FOR_MOVIE_SEARCH);
            }
        });

        /**
         * 1. recyclerview 초기화
         */
        recyclerView = (RecyclerView) findViewById(R.id.specific__Recyclerview);
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
        mDatas = new ArrayList<DetailDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!
        //7,8번째 파라미터는 if문으로 값이 0,1 일때 각각 다른이미지가 뜨도록 해야함!!
        mDatas.add(new DetailDatas(R.drawable.back, R.drawable.back, "owner", "페런트트랩", "1998", "이필주", "미국", R.drawable.back, R.drawable.back, "2016"));
        mDatas.add(new DetailDatas(R.drawable.back, R.drawable.back, "by,남채은", "매드맥스", "2015", "홍수빈", "미국", R.drawable.back, R.drawable.back, "2016"));
        mDatas.add(new DetailDatas(R.drawable.back, R.drawable.back, "by,하태경", "해리포터", "2000", "김지원", "영국", R.drawable.back, R.drawable.back, "2016"));

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        DetailAdapter adapter = new DetailAdapter(mDatas, recylerClickListener);

        recyclerView.setAdapter(adapter);

    }
    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

            String title = mDatas.get(position).title;
            String year = mDatas.get(position).year;
            String directer = mDatas.get(position).directer;
            String country = mDatas.get(position).country;
            String likecount = mDatas.get(position).likecount;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(SpecificBasketActivity.this, position+"번째 리사이클러뷰 항목 클릭!"+title+"/"+directer+"/"+likecount, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_FOR_MOVIE_SEARCH:
                if(resultCode == RESULT_OK){

                }
                break;
        }
    }
}
