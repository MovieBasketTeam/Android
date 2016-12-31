package com.moviebasket.android.client.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.basket_detail.SpecificBasketActivity;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.mypage.basket_list.BasketListActivity;
import com.moviebasket.android.client.mypage.basket_list.BasketListAdapter;
import com.moviebasket.android.client.mypage.basket_list.BasketListDataResult;
import com.moviebasket.android.client.mypage.basket_list.BasketListDatas;
import com.moviebasket.android.client.mypage.movie_pack_list.MoviePackActivity;
import com.moviebasket.android.client.mypage.movie_rec_list.MovieRecActivity;
import com.moviebasket.android.client.mypage.setting.SettingActivity;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.search.MovieSearchActivity;
import com.moviebasket.android.client.tag.hashtag.HashTagActivity;
import com.moviebasket.android.client.testpage.JsoupActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String member_token;
    private final String[] nav_item_main = {"담은 바스켓", "담은 영화", "추천한 영화", "테스트용임ㅋㅋ", "실습용", "세팅", "로그아웃 확인"};
    private static final int REQEUST_CODE_FOR_BASKET_LIST = 1000;
    private static final int REQEUST_CODE_FOR_MOVIE_PACK = 1001;
    private static final int REQEUST_CODE_FOR_MOVIE_REC = 1002;
    private static final int REQEUST_CODE_FOR_TEST = 1003;
    private static final int REQEUST_CODE_FOR_PRACTICE = 1004;
    private static final int REQEUST_CODE_FOR_SPECIFIC_BASKET = 1005;
    private static final int REQEUST_CODE_FOR_HASHTAG = 1006;
    private static final int REQEUST_CODE_FOR_SETTING = 1007;

    MBService mbService;

    RecyclerView rv;
    LinearLayoutManager layoutManager;
    ArrayList<BasketListDatas> basketListDatases;
    BasketListAdapter basketListAdapter;

    DrawerLayout drawerLayout;
    ListView listView;
    LinearLayout linearLayout;
    ImageView btn_toggle, btn_tag, newbtn, popularbtn, recommendbtn;

    /*
    FloatingActionMenu fab_menu;
    FloatingActionButton fab_item1, fab_item2, fab_item3;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        member_token = ApplicationController.getInstance().getPreferences();
        mbService = ApplicationController.getInstance().getMbService();

        //바스켓리스트 사용자 추천순으로 가져와야함. (MBService)에서
        loadBasketListDatas(1);

        //변수초기화
        drawerLayout = (DrawerLayout) findViewById(R.id.dllayout_drawer_main);
        listView = (ListView) findViewById(R.id.listview_nav_item_main);
        linearLayout = (LinearLayout) findViewById(R.id.lilayout_nav_drawer_main);
        btn_toggle = (ImageView) findViewById(R.id.btn_toggle_drawer_main);
        btn_tag = (ImageView) findViewById(R.id.btn_tag_main);
        newbtn = (ImageView) findViewById(R.id.newbtn);
        popularbtn = (ImageView) findViewById(R.id.popularbtn);
        recommendbtn= (ImageView) findViewById(R.id.recommendbtn);

        /*
        fab_menu = (FloatingActionMenu)findViewById(R.id.floating_action_menu);
        fab_item1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item1);
        fab_item2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item2);
        fab_item3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item3);

        fab_item1.setOnClickListener(fabClickListener);
        fab_item2.setOnClickListener(fabClickListener);
        fab_item3.setOnClickListener(fabClickListener);
        */

        rv = (RecyclerView) findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);


        listView.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nav_item_main));
        listView.setOnItemClickListener(new DrawerItemClickListener());

        btn_toggle.setOnClickListener(clickListener);
        btn_tag.setOnClickListener(clickListener);
        newbtn.setOnClickListener(clickListener);
        popularbtn.setOnClickListener(clickListener);
        recommendbtn.setOnClickListener(clickListener);


        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);

                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                int scrollExtend = recyclerView.computeVerticalScrollExtent();
                int scrollRange = recyclerView.computeVerticalScrollRange();

                if (scrollOffset + scrollExtend == scrollRange || scrollOffset + scrollExtend - 1 == scrollRange) {
                    Toast.makeText(getApplicationContext(),"맨아래",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });

        if(basketListDatases==null)
            basketListDatases = new ArrayList<>();
        if(basketListAdapter==null)
            basketListAdapter = new BasketListAdapter(basketListDatases, recylerClickListener);

        //rv.setAdapter(basketListAdapter);
        basketListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {

        //드로워 열려 있으면 닫기만 하기.
        if(drawerLayout.isDrawerOpen(linearLayout)) {
            drawerLayout.closeDrawer(linearLayout);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.super.onBackPressed();
            }
        });
        builder.setMessage("어플을 종료하시겠습니까?");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_tag_main:
                    Intent tagIntent = new Intent(MainActivity.this, HashTagActivity.class);
                    startActivityForResult(tagIntent, REQEUST_CODE_FOR_HASHTAG);
                    break;
                case R.id.btn_toggle_drawer_main:
                    drawerLayout.openDrawer(linearLayout);
                    break;
                case R.id.newbtn:
                    newbtn.setBackgroundResource(R.drawable.main_recent_black);
                    popularbtn.setBackgroundResource(R.drawable.main_pop);
                    recommendbtn.setBackgroundResource(R.drawable.main_reco);
                    loadBasketListDatas(1);
                    break;
                case R.id.popularbtn:
                    newbtn.setBackgroundResource(R.drawable.main_recent);
                    popularbtn.setBackgroundResource(R.drawable.main_pop_black);
                    recommendbtn.setBackgroundResource(R.drawable.main_reco);
                    loadBasketListDatas(2);
                    break;
                case R.id.recommendbtn:
                    newbtn.setBackgroundResource(R.drawable.main_recent);
                    popularbtn.setBackgroundResource(R.drawable.main_pop);
                    recommendbtn.setBackgroundResource(R.drawable.main_reco_black);
                    loadBasketListDatas(3);
                    break;

            }
        }
    };
    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = rv.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법
            //String basketName = basketListDatases.get(position).basketName;
          //  String basketDownCount = basketListDatases.get(position).downCount;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(MainActivity.this, position+"번째 리사이클러뷰 항목 클릭!"+" / "+basketListDatases.get(position).basket_name, Toast.LENGTH_SHORT).show();
            Intent specificBasketIntent = new Intent(MainActivity.this, SpecificBasketActivity.class);
            //SpecificBasket에 무슨 바스켓을 선택했는지에 대한 정보를 보내줘야함.
            startActivityForResult(specificBasketIntent, REQEUST_CODE_FOR_SPECIFIC_BASKET);
        }
    };

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            switch (position) {

                case 0:
                    Intent BasketListIntent = new Intent(MainActivity.this, BasketListActivity.class);
                    startActivityForResult(BasketListIntent, REQEUST_CODE_FOR_BASKET_LIST);
                    break;
                case 1:
                    Intent moviePackIntent = new Intent(MainActivity.this, MoviePackActivity.class);
                    startActivityForResult(moviePackIntent, REQEUST_CODE_FOR_MOVIE_PACK);
                    break;
                case 2:
                    Intent movieRecIntent = new Intent(MainActivity.this, MovieRecActivity.class);
                    startActivityForResult(movieRecIntent, REQEUST_CODE_FOR_MOVIE_REC);
                    break;
                case 3:
                    //테스트용
                    Intent testIntent = new Intent(MainActivity.this, MovieSearchActivity.class);
                    startActivityForResult(testIntent, REQEUST_CODE_FOR_TEST);
                    break;
                case 4:
                    Intent intent = new Intent(MainActivity.this, JsoupActivity.class);
                    startActivityForResult(intent, REQEUST_CODE_FOR_PRACTICE );
                    break;
                case 5:
                    Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivityForResult(settingIntent, REQEUST_CODE_FOR_SETTING );
                    break;
                case 6:
                    //로그아웃 확인하는 거
                    ApplicationController.getInstance().savePreferences("");
                    break;
            }
            drawerLayout.closeDrawer(linearLayout);
        }
    }

    /*
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.floating_action_menu_item1:
                    Toast.makeText(MainActivity.this, fab_item1.getLabelText(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.floating_action_menu_item2:
                    Toast.makeText(MainActivity.this, fab_item2.getLabelText(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.floating_action_menu_item3:
                    Toast.makeText(MainActivity.this, fab_item3.getLabelText(), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQEUST_CODE_FOR_BASKET_LIST:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_MOVIE_PACK:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_MOVIE_REC:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_TEST:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_PRACTICE:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_SPECIFIC_BASKET:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_HASHTAG:
                if (resultCode == RESULT_OK) {

                }
                break;
            case REQEUST_CODE_FOR_SETTING:
                if (resultCode == RESULT_OK) {

                }
                break;
        }
    }

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
                    rv.setAdapter(basketListAdapter);
                    Log.i("NetConfirm", "onResponse: rv.setAdapter확인");
                    basketListAdapter.notifyDataSetChanged();
                }else{
                    basketListDatases = new ArrayList<BasketListDatas>();
                    Toast.makeText(MainActivity.this, "바스켓 리스트를 가져오는 데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BasketListDataResult> call, Throwable t) {
                //바스켓리스트를 가져오는데 실패함
                Toast.makeText(MainActivity.this, "서버와 연결에 문제가 생겼습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}