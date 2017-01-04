package com.moviebasket.android.client.basket_detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.moviebasket.android.client.clickable.TwoClickable;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.movie_detail.MovieDetailDialog;
import com.moviebasket.android.client.mypage.movie_rec_list.HeartResult;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.search.MovieSearchActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpecificBasketActivity extends AppCompatActivity implements TwoClickable {

    private static final int REQUEST_CODE_FOR_MOVIE_SEARCH = 1000;

    private MBService mbService;
    DetailAdapter adapter;
    Button btn_add_movie;

    ImageView basketImg;
    TextView basketName;
    ImageView downBtn;
    TextView downCount;

    int basket_id;
    int basket_count;
    String token;

    private MovieDetailDialog detailDialog;

    private boolean isHeartSuccess = false;
    private boolean isCarttSuccess = false;

    RecyclerView recyclerView;
    ArrayList<DetailDatas> mDatas = new ArrayList<DetailDatas>();

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_basket);

        Intent basketInfo  = getIntent();
        mbService = ApplicationController.getInstance().getMbService();


        basket_id = basketInfo.getExtras().getInt("basket_id");
        basket_count = basketInfo.getExtras().getInt("basket_like");

        Log.i("Info_BasketId : ", String.valueOf(basket_id));

        basketImg = (ImageView)findViewById(R.id.basketImg);
        basketName = (TextView)findViewById(R.id.basketName);
        downBtn = (ImageView)findViewById(R.id.specificDownBtn);
        downCount = (TextView)findViewById(R.id.downCount);


        Glide.with(getApplicationContext()).load(basketInfo.getExtras().getString("basket_image")).into(basketImg);
        basketName.setText(basketInfo.getExtras().getString("basket_name"));
        if ( basketInfo.getExtras().getInt("is_liked") == 0 ) {
            downBtn.setImageResource(R.drawable.sub_basket_nodown);
        } else {
            downBtn.setImageResource(R.drawable.sub_basket_down);
        }
        downCount.setText(String.valueOf(basket_count));

/*        Log.i("Info : ", basketInfo.getExtras().getInt("basket_id")+"/"+basketInfo.getExtras().getString("basket_name")+"/"
                +basketInfo.getExtras().getString("basket_image")+"/"+basketInfo.getExtras().getInt("basket_like")+"/"+
        +basketInfo.getExtras().getInt("is_liked"));*/

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clickConfirm", "onClick: sdfsdfsdfsdfsdf");
                Toast.makeText(SpecificBasketActivity.this, "ㅗㅇㄹㄴㅇㄹㄴㅇㄹ", Toast.LENGTH_SHORT).show();

                }
                //Toast.makeText(SpecificBasketActivity.this, "담기클릭", Toast.LENGTH_SHORT);
                /*Toast.makeText(BasketListActivity.this, "담은바스켓취소", Toast.LENGTH_SHORT).show();
                Call<BasketResult> getCartResult = mbService.getCartResult(mDatas.get(position).basket_id, token);
                getCartResult.enqueue(new Callback<BasketResult>() {
                    @Override
                    public void onResponse(Call<BasketResult> call, Response<BasketResult> response) {
                        BasketResult basketResult = response.body();
                        if (response.isSuccessful()) {// 응답코드 200
                            isCartSuccess = true;
                        }
                        if (isCartSuccess) {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<BasketResult> call, Throwable t) {
                        Toast.makeText(BasketListActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                    }*/
        });

        btn_add_movie = (Button)findViewById(R.id.btn_add_movie_specific);
        btn_add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movieSearchIntent = new Intent(SpecificBasketActivity.this, MovieSearchActivity.class);
                movieSearchIntent.putExtra("basket_id", basket_id);
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
        token = ApplicationController.getInstance().getPreferences();

        //바스켓 리스트를 가져온다.
        loadBasketList();

    }
    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);

            detailDialog = new MovieDetailDialog(SpecificBasketActivity.this);
            detailDialog.show();
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        loadBasketList();
        //데이터 다시 불러오기
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_FOR_MOVIE_SEARCH:
                if(resultCode == RESULT_OK){
                    Toast.makeText(this, "영화를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void loadBasketList(){
        Call<DetailResultParent> getBasketDetail = mbService.getBasketDetail(token, basket_id);
        getBasketDetail.enqueue(new Callback<DetailResultParent>() {
            @Override
            public void onResponse(Call<DetailResultParent> call, Response<DetailResultParent> response) {
                Log.i("NetConfirm", "onResponse: 들어옴");

                DetailResultParent recResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("recommendMovie Test", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                    mDatas.clear();
                    mDatas.addAll(recResult.result.result);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DetailResultParent> call, Throwable t) {
                Log.i("NetConfirm", "onFailure: 들어옴");

                Toast.makeText(SpecificBasketActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.i("recommendMovie Test", "요청메시지:" + call.toString());
            }
        });

        adapter = new DetailAdapter(mDatas, recylerClickListener, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void processOneMethodAtPosition(final int position) {
        Call<HeartResult> getHeartReasult = mbService.getHeartResult(mDatas.get(position).movie_id, mDatas.get(position).is_liked, token);
        getHeartReasult.enqueue(new Callback<HeartResult>() {
            @Override
            public void onResponse(Call<HeartResult> call, Response<HeartResult> response) {
                HeartResult heartResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    isHeartSuccess = heartResult.result.message != null ? true : false;
                }
                if (isHeartSuccess) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HeartResult> call, Throwable t) {
                Toast.makeText(SpecificBasketActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }


    //TODO : 영화 담기 수정해야함.
    @Override
    public void processTwoMethodAtPosition(final int position) {
        if(mDatas.get(position).is_cart == 0) {
            Call<HeartResult> getMovieCartReasult = mbService.getMovieCartReasult(mDatas.get(position).movie_id, mDatas.get(position).is_cart, token);
            getMovieCartReasult.enqueue(new Callback<HeartResult>() {
                @Override
                public void onResponse(Call<HeartResult> call, Response<HeartResult> response) {
                    Log.i("Cart", "onResponse: 2번메서드(담기)");
                    HeartResult heartResult = response.body();
                    if (response.isSuccessful()) {// 응답코드 200
                        Log.i("Cart", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                        Log.i("Cart", "응답 결과 : " + heartResult.result.message);
                        isCarttSuccess = heartResult.result.message != null ? true : false;
                        Log.i("Cart", "응답 결과 : " + isCarttSuccess);
                        Log.i("Cart", "포지션 : " + mDatas.get(position));
                        Log.i("Cart", "무비아이디 : " + mDatas.get(position).movie_id);
                        Log.i("Cart", "Cart : " + mDatas.get(position).is_cart);
                        if (isCarttSuccess) {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "영화담기실패", Toast.LENGTH_SHORT);
                    }

                }

                @Override
                public void onFailure(Call<HeartResult> call, Throwable t) {
                    Log.i("Cart", "onFailure: 들어옴" + call.toString());
                    Toast.makeText(SpecificBasketActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "이미 영화를 담았습니다.", Toast.LENGTH_SHORT);
        }
    }

}
