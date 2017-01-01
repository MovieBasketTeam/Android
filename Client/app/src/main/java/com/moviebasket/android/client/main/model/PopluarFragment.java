package com.moviebasket.android.client.main.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.basket_detail.SpecificBasketActivity;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.mypage.basket_list.BasketListAdapter;
import com.moviebasket.android.client.mypage.basket_list.BasketListDataResult;
import com.moviebasket.android.client.mypage.basket_list.BasketListDatas;
import com.moviebasket.android.client.network.MBService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kh on 2017. 1. 1..
 */

public class PopluarFragment extends Fragment {

    private static final int REQEUST_CODE_FOR_SPECIFIC_BASKET = 1005;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<BasketListDatas> basketListDatases;
    BasketListAdapter basketListAdapter;

    MBService mbService;
    private String member_token;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.viewpage_main_view, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.myRecyclerview);

        mbService = ApplicationController.getInstance().getMbService();

        member_token = ApplicationController.getInstance().getPreferences();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        basketListAdapter = new BasketListAdapter(basketListDatases, recylerClickListener);

        loadBasketListDatas(2);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                int scrollExtend = recyclerView.computeVerticalScrollExtent();
                int scrollRange = recyclerView.computeVerticalScrollRange();

                if (scrollOffset + scrollExtend == scrollRange || scrollOffset + scrollExtend - 1 == scrollRange) {
                    Toast.makeText(getActivity(),"맨아래",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });

        recyclerView.setAdapter(basketListAdapter);


        return view;
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법
            //String basketName = basketListDatases.get(position).basketName;
            //  String basketDownCount = basketListDatases.get(position).downCount;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(getActivity(), position+"번째 리사이클러뷰 항목 클릭!"+" / "+basketListDatases.get(position).basket_name, Toast.LENGTH_SHORT).show();

            Intent specificBasketIntent = new Intent(getContext(), SpecificBasketActivity.class);
            //SpecificBasket에 무슨 바스켓을 선택했는지에 대한 정보를 보내줘야함.

            specificBasketIntent.putExtra("basket_id", basketListDatases.get(position).basket_id);
            specificBasketIntent.putExtra("basket_name", basketListDatases.get(position).basket_name);
            specificBasketIntent.putExtra("basket_image", basketListDatases.get(position).basket_image);
            specificBasketIntent.putExtra("basket_like", basketListDatases.get(position).basket_like);
            specificBasketIntent.putExtra("is_liked", basketListDatases.get(position).is_liked);

            startActivityForResult(specificBasketIntent, REQEUST_CODE_FOR_SPECIFIC_BASKET);
        }
    };


    private void loadBasketListDatas(int mode){
        Call<BasketListDataResult> getRecommendedBasketList = mbService.getBasketListDataResultOrderBy(member_token, mode);
        getRecommendedBasketList.enqueue(new Callback<BasketListDataResult>() {
            @Override
            public void onResponse(Call<BasketListDataResult> call, Response<BasketListDataResult> response) {
                //바스켓리스트 가져옴.
                BasketListDataResult result = response.body();
                String message = result.result.message;
                if(message==null){
                    basketListDatases = result.result.baskets;

                    Log.i("NetConfirm", "onResponse: basketListData is null? in 서버요청 : "+basketListDatases.toString());
                    basketListAdapter = new BasketListAdapter(basketListDatases, recylerClickListener);
                    recyclerView.setAdapter(basketListAdapter);
                    Log.i("NetConfirm", "onResponse: rv.setAdapter확인");
                    basketListAdapter.notifyDataSetChanged();
                }else{
                    basketListDatases = new ArrayList<BasketListDatas>();
                    Toast.makeText(getActivity(), "바스켓 리스트를 가져오는 데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BasketListDataResult> call, Throwable t) {
                //바스켓리스트를 가져오는데 실패함
                Toast.makeText(getActivity(), "서버와 연결에 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}