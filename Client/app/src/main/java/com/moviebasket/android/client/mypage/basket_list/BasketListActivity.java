package com.moviebasket.android.client.mypage.basket_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.basket_detail.SpecificBasketActivity;
import com.moviebasket.android.client.clickable.OneClickable;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.mypage.movie_rec_list.HeartResult;
import com.moviebasket.android.client.network.MBService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moviebasket.android.client.R.id.basketName;
import static com.moviebasket.android.client.R.id.downCount;

public class BasketListActivity extends AppCompatActivity implements OneClickable{

    RecyclerView recyclerView;
    ArrayList<BasketListDatas> mDatas = new ArrayList<BasketListDatas>();

    LinearLayoutManager mLayoutManager;
    private MBService mbService;
    private boolean isCartSuccess = false;
    private static final int REQEUST_CODE_FOR_BASKET_REC = 1000;;
    BasketListAdapter adapter;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_list);

        mbService = ApplicationController.getInstance().getMbService();

        /**
         * 1. recyclerview 초기화
         */
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerview);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        mDatas = new ArrayList<>();
        /**
         * 2. recyclerview에 보여줄 data
         */
        mDatas = new ArrayList<BasketListDatas>();
        token = ApplicationController.getInstance().getPreferences();
        if (!token.equals("")) {
            Call<BasketListDataResult> BasketListData = mbService.getMyBasketListResult(token);
            BasketListData.enqueue(new Callback<BasketListDataResult>() {
                @Override
                public void onResponse(Call<BasketListDataResult> call, Response<BasketListDataResult> response) {
                    if (response.isSuccessful()) {
                        BasketListDataResult basketList = response.body();

                        mDatas.addAll(basketList.result.baskets);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<BasketListDataResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "로그인을 해주세요.", Toast.LENGTH_SHORT);
        }

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        adapter = new BasketListAdapter(mDatas, recylerClickListener, this);
        recyclerView.setAdapter(adapter);
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);

            //2.position번째 항목의 Data를 가져오는 방법
            // String basketName = mDatas.get(position).basketName;

            Toast.makeText(BasketListActivity.this, position + "번째 리사이클러뷰 항목 클릭!" + basketName + "/" + downCount, Toast.LENGTH_SHORT).show();
            Log.i("바스켓 클릭!! ", "햇다");

//            Intent SpecificBasketActivity = new Intent(BasketListActivity.this, SpecificBasketActivity.class);
//            startActivity(SpecificBasketActivity);

            Intent specificBasketIntent = new Intent(BasketListActivity.this, SpecificBasketActivity.class);
            //SpecificBasket에 무슨 바스켓을 선택했는지에 대한 정보를 보내줘야함.

            specificBasketIntent.putExtra("basket_id", mDatas.get(position).basket_id);
            specificBasketIntent.putExtra("basket_name", mDatas.get(position).basket_name);
            specificBasketIntent.putExtra("basket_image", mDatas.get(position).basket_image);
            specificBasketIntent.putExtra("basket_like", mDatas.get(position).basket_like);
            specificBasketIntent.putExtra("is_liked", mDatas.get(position).is_liked);

            startActivityForResult(specificBasketIntent, REQEUST_CODE_FOR_BASKET_REC);


        }
    };

    @Override
    public void processOneMethodAtPosition(final int position) {

        Toast.makeText(BasketListActivity.this, "담은바스켓취소", Toast.LENGTH_SHORT).show();
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
            }
        });


    }
}

