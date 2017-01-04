package com.moviebasket.android.client.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.main.presenter.ImagePagerAdatper;
import com.moviebasket.android.client.main.presenter.PagerAdapter;
import com.moviebasket.android.client.mypage.basket_list.BasketListActivity;
import com.moviebasket.android.client.mypage.movie_pack_list.MoviePackActivity;
import com.moviebasket.android.client.mypage.movie_rec_list.MovieRecActivity;
import com.moviebasket.android.client.mypage.setting.SettingActivity;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.search.MovieSearchActivity;
import com.moviebasket.android.client.splash.SplashActivity;
import com.moviebasket.android.client.tag.hashtag.HashTagActivity;
import com.moviebasket.android.client.testpage.JsoupActivity;

import java.util.Timer;
import java.util.TimerTask;

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

    DrawerLayout drawerLayout;
    ListView listView;
    LinearLayout linearLayout;
    ImageView btn_toggle, btn_tag, newbtn, popularbtn, recommendbtn;

    /*
    FloatingActionMenu fab_menu;
    FloatingActionButton fab_item1, fab_item2, fab_item3;
    */

    ViewPager viewPager;
    ViewPager imageViewPager;
    PagerAdapter pagerAdapter;
    ImagePagerAdatper imagePagerAdapter;

    int pageNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        member_token = ApplicationController.getInstance().getPreferences();
        mbService = ApplicationController.getInstance().getMbService();

        //변수초기화
        pageNumber = 0;
        drawerLayout = (DrawerLayout) findViewById(R.id.dllayout_drawer_main);
        listView = (ListView) findViewById(R.id.listview_nav_item_main);
        linearLayout = (LinearLayout) findViewById(R.id.lilayout_nav_drawer_main);
        btn_toggle = (ImageView) findViewById(R.id.btn_toggle_drawer_main);
        btn_tag = (ImageView) findViewById(R.id.btn_tag_main);
        newbtn = (ImageView) findViewById(R.id.newbtn);
        popularbtn = (ImageView) findViewById(R.id.popularbtn);
        recommendbtn = (ImageView) findViewById(R.id.recommendbtn);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageViewPager = (ViewPager) findViewById(R.id.imageViewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        imagePagerAdapter = new ImagePagerAdatper(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        imageViewPager.setAdapter(imagePagerAdapter);


        /*
        fab_menu = (FloatingActionMenu)findViewById(R.id.floating_action_menu);
        fab_item1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item1);
        fab_item2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item2);
        fab_item3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item3);

        fab_item1.setOnClickListener(fabClickListener);
        fab_item2.setOnClickListener(fabClickListener);
        fab_item3.setOnClickListener(fabClickListener);
        */

        listView.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nav_item_main));
        listView.setOnItemClickListener(new DrawerItemClickListener());

        btn_toggle.setOnClickListener(clickListener);
        btn_tag.setOnClickListener(clickListener);
        newbtn.setOnClickListener(clickListener);
        popularbtn.setOnClickListener(clickListener);
        recommendbtn.setOnClickListener(clickListener);

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(pageNumber==2)
                    pageNumber = 0;
                imageViewPager.setCurrentItem(pageNumber++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 500, 3000);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        newbtn.setBackgroundResource(R.drawable.main_recent_black);
                        popularbtn.setBackgroundResource(R.drawable.main_pop);
                        recommendbtn.setBackgroundResource(R.drawable.main_reco);

                        break;
                    case 1:
                        newbtn.setBackgroundResource(R.drawable.main_recent);
                        popularbtn.setBackgroundResource(R.drawable.main_pop_black);
                        recommendbtn.setBackgroundResource(R.drawable.main_reco);

                        break;

                    case 2:
                        newbtn.setBackgroundResource(R.drawable.main_recent);
                        popularbtn.setBackgroundResource(R.drawable.main_pop);
                        recommendbtn.setBackgroundResource(R.drawable.main_reco_black);

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;

                    case 2:

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onBackPressed() {

        //드로워 열려 있으면 닫기만 하기.
        if (drawerLayout.isDrawerOpen(linearLayout)) {
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
                    overridePendingTransition( R.anim.slide_in_left, R.anim.hold );

                    break;
                case R.id.btn_toggle_drawer_main:
                    drawerLayout.openDrawer(linearLayout);
                    break;
                case R.id.newbtn:
                    newbtn.setBackgroundResource(R.drawable.main_recent_black);
                    popularbtn.setBackgroundResource(R.drawable.main_pop);
                    recommendbtn.setBackgroundResource(R.drawable.main_reco);

                    viewPager.setCurrentItem(0);
                    break;
                case R.id.popularbtn:
                    newbtn.setBackgroundResource(R.drawable.main_recent);
                    popularbtn.setBackgroundResource(R.drawable.main_pop_black);
                    recommendbtn.setBackgroundResource(R.drawable.main_reco);
//                    loadBasketListDatas(2);

                    viewPager.setCurrentItem(1);
                    break;
                case R.id.recommendbtn:
                    newbtn.setBackgroundResource(R.drawable.main_recent);
                    popularbtn.setBackgroundResource(R.drawable.main_pop);
                    recommendbtn.setBackgroundResource(R.drawable.main_reco_black);
//                    loadBasketListDatas(3)
                    viewPager.setCurrentItem(2);

                    break;

            }
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
                    overridePendingTransition( R.anim.slide_in_right, R.anim.hold );

                    break;
                case 1:
                    Intent moviePackIntent = new Intent(MainActivity.this, MoviePackActivity.class);
                    startActivityForResult(moviePackIntent, REQEUST_CODE_FOR_MOVIE_PACK);
                    overridePendingTransition( R.anim.slide_in_up, R.anim.hold );

                    break;
                case 2:
                    Intent movieRecIntent = new Intent(MainActivity.this, MovieRecActivity.class);
                    startActivityForResult(movieRecIntent, REQEUST_CODE_FOR_MOVIE_REC);
                    overridePendingTransition( R.anim.slide_in_right, R.anim.hold );

                    break;
                case 3:
                    //테스트용
                    Intent testIntent = new Intent(MainActivity.this, MovieSearchActivity.class);
                    startActivityForResult(testIntent, REQEUST_CODE_FOR_TEST);
                    break;
                case 4:
                    Intent intent = new Intent(MainActivity.this, JsoupActivity.class);
                    startActivityForResult(intent, REQEUST_CODE_FOR_PRACTICE);
                    break;
                case 5:
                    Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivityForResult(settingIntent, REQEUST_CODE_FOR_SETTING);
                    break;
                case 6:
                    //토큰값 지우기
                    ApplicationController.getInstance().savePreferences("");
                    //스플래시 화면으로 가기.
                    Intent logoutIntent = new Intent(MainActivity.this, SplashActivity.class);
                    //액티비티 스택 clear
                    logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logoutIntent);
                    finish();
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

    @Override
    protected void onResume() {
        super.onResume();

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }
}