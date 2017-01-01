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
        String token = ApplicationController.getInstance().getPreferences();
        if (!token.equals("")) {
            Call<BasketListDataResult> BasketListData = mbService.getMyBasketListResult(token);
            BasketListData.enqueue(new Callback<BasketListDataResult>() {
                @Override
                public void onResponse(Call<BasketListDataResult> call, Response<BasketListDataResult> response) {
                    if (response.isSuccessful()) {
                        BasketListDataResult basketList = response.body();

                        mDatas.addAll(basketList.result.baskets);
                        Log.i("log", basketList.result.baskets.get(0).basket_name);
                        Log.i("log", String.valueOf(basketList.result.baskets.get(0).basket_like));
                        Log.i("log", String.valueOf(basketList.result.baskets.get(0).is_liked));

                        Log.i("baskets : ", mDatas.get(0).basket_name);
                        Log.i("baskets : ", String.valueOf(mDatas.get(0).basket_like));
                        Log.i("baskets : ", String.valueOf(mDatas.get(0).is_liked));
                        Log.i("baskets : ", String.valueOf(mDatas.get(0).basket_id));
                        Log.i("baskets : ", mDatas.get(0).basket_image);


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

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

            // String basketName = mDatas.get(position).basketName;
            // String downCount = mDatas.get(position).downCount;


            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(BasketListActivity.this, position + "번째 리사이클러뷰 항목 클릭!" + basketName + "/" + downCount, Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener subClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.downBtn:
                    //바스켓 담기|제거버튼
                    AlertDialog.Builder BasketBuilder = new AlertDialog.Builder(v.getContext());
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

