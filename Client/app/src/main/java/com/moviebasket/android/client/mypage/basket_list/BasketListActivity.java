/*
package com.moviebasket.android.client.mypage.basket_list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import static com.moviebasket.android.client.R.id.basketName;
import static com.moviebasket.android.client.R.id.downCount;

public class BasketListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BasketListDatas> mDatas = new ArrayList<BasketListDatas>();

    LinearLayoutManager mLayoutManager;
    private MBService mbService;
    BasketListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_list);

        mbService = ApplicationController.getInstance().getMbService();

        */
/**
         * 1. recyclerview 초기화
         *//*

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerview);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        mDatas = new ArrayList<>();
        */
/**
         * 2. recyclerview에 보여줄 data
         *//*

        mDatas = new ArrayList<BasketListDatas>();
        String token = ApplicationController.getInstance().getPreferences();
        if (!token.equals("")) {
            Call<BasketListDataResult> BasketListData = mbService.getMyBasketListResult(token);
            BasketListData.enqueue(new Callback<BasketListDataResult>() {
                @Override
                public void onResponse(Call<BasketListDataResult> call, Response<BasketListDataResult> response) {
                    if (response.isSuccessful()) {
                        BasketListDataResult basketList = response.body();

                        mDatas.addAll(basketList.result.baskets);

                        adapter = new BasketListAdapter(mDatas, recylerClickListener, subClickListener);
                        recyclerView.setAdapter(adapter);
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

        */
/**
         * 3. Adapter 생성 후 recyclerview에 지정
         *//*

    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);

            //2.position번째 항목의 Data를 가져오는 방법
            // String basketName = mDatas.get(position).basketName;
            Toast.makeText(BasketListActivity.this, position + "번째 리사이클러뷰 항목 클릭!" + basketName + "/" + downCount, Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener subClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            int position = recyclerView.getChildLayoutPosition(v);
            switch (v.getId()) {
                case R.id.downBtn:
                    //바스켓 담기|제거버튼
                    AlertDialog.Builder BasketBuilder = new AlertDialog.Builder(v.getContext());
                    BasketBuilder.setMessage("삭제할래요?");
                    BasketBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    BasketBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(v.getContext(), "바스켓을 담았다고 치자", Toast.LENGTH_SHORT).show();
                        }
                    });
                    BasketBuilder.show();
                    break;
            }
        }
    };
}

*/


package com.moviebasket.android.client.mypage.basket_list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.moviebasket.android.client.R;
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
                Log.i("Cart", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                Log.i("Cart", "응답 결과 : " + basketResult.result.message);
                if (response.isSuccessful()) {// 응답코드 200
                    isCartSuccess = true;
                    Log.i("Cart", "응답 결과 : " + isCartSuccess);
                    Log.i("Cart", "포지션 : " + mDatas.get(position));
                    Log.i("Cart", "바스켓아이디 : " + mDatas.get(position).basket_id);
                }
                if (isCartSuccess) {
                    mDatas.remove(position);
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

