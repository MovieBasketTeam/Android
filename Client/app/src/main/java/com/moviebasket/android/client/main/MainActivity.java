package com.moviebasket.android.client.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.moviebasket.android.client.R;
import com.moviebasket.android.client.mypage.basket_list.BasketListActivity;
import com.moviebasket.android.client.mypage.movie_pack_list.MoviePackActivity;
import com.moviebasket.android.client.mypage.movie_rec_list.MovieRecActivity;

public class MainActivity extends AppCompatActivity {

    private final String[] nav_item_main = {"담은 바스켓", "담은 영화", "추천한 영화"};
    private static final int REQEUST_CODE_FOR_BASKET_LIST = 1000;
    private static final int REQEUST_CODE_FOR_MOVIE_PACK = 1001;
    private static final int REQEUST_CODE_FOR_MOVIE_REC = 1002;

    RecyclerView rv;
    LinearLayoutManager layoutManager;

    DrawerLayout drawerLayout;
    ListView listView;
    LinearLayout linearLayout;
    ImageButton btn_toggle;

    FloatingActionMenu fab_menu;
    FloatingActionButton fab_item1, fab_item2, fab_item3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //변수초기화
        drawerLayout = (DrawerLayout)findViewById(R.id.dllayout_drawer_main);
        listView = (ListView) findViewById(R.id.listview_nav_item_main);
        linearLayout = (LinearLayout) findViewById(R.id.lilayout_nav_drawer_main);
        btn_toggle = (ImageButton)findViewById(R.id.btn_toggle_drawer_main);

        fab_menu = (FloatingActionMenu)findViewById(R.id.floating_action_menu);
        fab_item1 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item1);
        fab_item2 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item2);
        fab_item3 = (FloatingActionButton) findViewById(R.id.floating_action_menu_item3);

        fab_item1.setOnClickListener(fabClickListener);
        fab_item2.setOnClickListener(fabClickListener);
        fab_item3.setOnClickListener(fabClickListener);

        rv = (RecyclerView)findViewById(R.id.rv_main);

        listView.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nav_item_main));
        listView.setOnItemClickListener(new DrawerItemClickListener());
        btn_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(linearLayout);
            }
        });

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            switch (position) {

                case 0:
                    Intent BasketListIntent = new Intent(MainActivity.this, BasketListActivity.class);
                    startActivityForResult(BasketListIntent, REQEUST_CODE_FOR_BASKET_LIST);
//                    Toast.makeText(MainActivity.this, nav_item_main[position], Toast.LENGTH_SHORT).show();
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
                    break;
                case 4:
                    break;
            }
            drawerLayout.closeDrawer(linearLayout);
        }
    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQEUST_CODE_FOR_BASKET_LIST:
                if(resultCode==RESULT_OK){

                }
                break;
            case REQEUST_CODE_FOR_MOVIE_PACK:
                if(resultCode==RESULT_OK){

                }
                break;
            case REQEUST_CODE_FOR_MOVIE_REC:
                if(resultCode==RESULT_OK){

                }
                break;
        }
    }
}
