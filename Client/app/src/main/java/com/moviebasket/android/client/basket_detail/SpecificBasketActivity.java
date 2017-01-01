package com.moviebasket.android.client.basket_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.search.MovieSearchActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpecificBasketActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_MOVIE_SEARCH = 1000;

    private MBService mbService;
    DetailAdapter adapter;
    Button btn_add_movie;

    ImageView basketImg;
    TextView basketName;
    ImageView downBtn;
    TextView downCount;


    RecyclerView recyclerView;
    ArrayList<DetailDatas> mDatas = new ArrayList<DetailDatas>();


    /*
    가장상단의 바스켓 정보 표시를 위하여 로드
     */
//    ArrayList<BasketListDatas> basketListDatases;
//    BasketListAdapter basketListAdapter;

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_basket);

        Intent basketInfo  = getIntent();
        mbService = ApplicationController.getInstance().getMbService();

        int basket_id;
        basket_id = basketInfo.getExtras().getInt("basket_id");

        Log.i("Info_BasketId : ", String.valueOf(basket_id));

        basketImg = (ImageView)findViewById(R.id.basketImg);
        basketName = (TextView)findViewById(R.id.basketName);
        downBtn = (ImageView)findViewById(R.id.downBtn);
        downCount = (TextView)findViewById(R.id.downCount);


        Glide.with(getApplicationContext()).load(basketInfo.getExtras().getString("basket_image")).into(basketImg);
        basketName.setText(basketInfo.getExtras().getString("basket_name"));
        if ( basketInfo.getExtras().getInt("is_liked") == 0 ) {
            downBtn.setImageResource(R.drawable.sub_movie_down);
        } else {
            downBtn.setImageResource(R.drawable.sub_movie_nodown);
        }
        downCount.setText(String.valueOf(basketInfo.getExtras().getString("downCount")));


/*        Log.i("Info : ", basketInfo.getExtras().getInt("basket_id")+"/"+basketInfo.getExtras().getString("basket_name")+"/"
                +basketInfo.getExtras().getString("basket_image")+"/"+basketInfo.getExtras().getInt("basket_like")+"/"+
        +basketInfo.getExtras().getInt("is_liked"));*/

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


        String token = ApplicationController.getInstance().getPreferences();
        Log.i("Info_Token : ",token);

        Call<DetailResultParent> getBasketDetail = mbService.getBasketDetail(token, basket_id);
        getBasketDetail.enqueue(new Callback<DetailResultParent>() {
            @Override
            public void onResponse(Call<DetailResultParent> call, Response<DetailResultParent> response) {
                Log.i("NetConfirm", "onResponse: 들어옴");

                DetailResultParent recResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("recommendMovie Test", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());

                    mDatas.addAll(recResult.result.result);
                    adapter.notifyDataSetChanged();
                }

                Log.i("myTag", String.valueOf(recResult.result.result.size()));

            }

            @Override
            public void onFailure(Call<DetailResultParent> call, Throwable t) {
                Log.i("NetConfirm", "onFailure: 들어옴");

                Toast.makeText(SpecificBasketActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.i("recommendMovie Test", "요청메시지:" + call.toString());
            }
        });



        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        adapter = new DetailAdapter(mDatas, recylerClickListener);
        recyclerView.setAdapter(adapter);

    }
    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

//            String basketName = mDatas.get(position).basketName;
            String BasketUserName = mDatas.get(position).movie_adder;
            String movieName = mDatas.get(position).movie_title;
            int year = mDatas.get(position).movie_pub_date;
            String director = mDatas.get(position).movie_director;
            int downCount = mDatas.get(position).movie_like;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            //Toast.makeText(SpecificBasketActivity.this, position+"번째 리사이클러뷰 항목 클릭!"+title+"/"+directer+"/"+likecount, Toast.LENGTH_SHORT).show();
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
