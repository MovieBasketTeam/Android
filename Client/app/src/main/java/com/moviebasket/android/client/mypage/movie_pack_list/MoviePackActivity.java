/*
package com.moviebasket.android.client.mypage.movie_pack_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.MBService;

import java.util.ArrayList;

public class MoviePackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PackDatas> mDatas = new ArrayList<PackDatas>();

    LinearLayoutManager mLayoutManager;
    private MBService mbService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pack);


        mbService = ApplicationController.getInstance().getMbService();


        */
/**
 * 1. recyclerview 초기화
 * <p>
 * 2. recyclerview에 보여줄 data
 * <p>
 * 3. Adapter 생성 후 recyclerview에 지정
 * <p>
 * 2. recyclerview에 보여줄 data
 * <p>
 * 3. Adapter 생성 후 recyclerview에 지정
 *//*

        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerview);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        // LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        */
/**
 * 2. recyclerview에 보여줄 data
 *//*

        mDatas = new ArrayList<PackDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!


*/
/*
        mDatas.add(new PackDatas("http://imgmovie.naver.com/mdi/mit110/0475/47528_P50_144916.jpg",
                "무비바스켓","+by. "+"존잘필", "라라랜드", "2016", "이필주", "11234",
                R.drawable.sub_heart, R.drawable.sub_movie_down));
*//*


        mDatas.add(new PackDatas("http://imgmovie.naver.com/mdi/mit110/0475/47528_P50_144916.jpg",
                "무비바스켓","+by. "+"존잘필", "라라랜드", "2016", "이필주", "11234",
                R.drawable.sub_heart, R.drawable.sub_movie_down));

        */
/**
 * 3. Adapter 생성 후 recyclerview에 지정
 *//*

        PackAdapter adapter = new PackAdapter(mDatas, recylerClickListener);

        recyclerView.setAdapter(adapter);
    }
    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

            String basketName = mDatas.get(position).basketName;
            String BasketUserName = mDatas.get(position).BasketUserName;
            String movieName = mDatas.get(position).movieName;
            String year = mDatas.get(position).year;
            String director = mDatas.get(position).director;
            String downCount = mDatas.get(position).downCount;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(MoviePackActivity.this, position+"번째 리사이클러뷰 항목 클릭!"+basketName+"/"+BasketUserName+"/"+movieName+"/"+year+"/"+director+"/"+downCount, Toast.LENGTH_SHORT).show();
        }
    };




}*/


package com.moviebasket.android.client.mypage.movie_pack_list;

import android.os.Bundle;
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

public class MoviePackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PackDatas> mDatas = new ArrayList<PackDatas>();
    ArrayList<PackDetail> packdetail;
    LinearLayoutManager mLayoutManager;
    private MBService mbService;

    PackResultResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_pack);

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

        /**
         * 2. recyclerview에 보여줄 data
         */
        mDatas = new ArrayList<PackDatas>();
        String token = ApplicationController.getInstance().getPreferences();
        if (!token.equals("")) {
            Call<PackResultResult> getMovieData = mbService.getMoviePackResult(token);
            getMovieData.enqueue(new Callback<PackResultResult>() {
                @Override
                public void onResponse(Call<PackResultResult> call, Response<PackResultResult> response) {
                    if (response.isSuccessful()) {
                        result = response.body();
                        mDatas.clear();
                        packdetail = result.result.result;

                        for (int i = 0; i < packdetail.size(); i++) {
/*                            Log.i("SOPT : ",packdetail.get(i).movie_image+"/"+packdetail.get(i).basket_name+"/"+packdetail.get(i).movie_director+"/"+
                                    packdetail.get(i).movie_title+"/"+packdetail.get(i).movie_pub_date+"/"+packdetail.get(i).movie_director+"/"+
                                    packdetail.get(i).movie_like+"/"+packdetail.get(i).is_liked+"/"+packdetail.get(i).is_cart);*/

                            mDatas.add(new PackDatas(packdetail.get(i).movie_image,
                                    packdetail.get(i).basket_name,
                                    packdetail.get(i).movie_director,
                                    packdetail.get(i).movie_title,
                                    packdetail.get(i).movie_pub_date,
                                    packdetail.get(i).movie_director,
                                    packdetail.get(i).movie_like,
                                    packdetail.get(i).is_liked,
                                    packdetail.get(i).is_cart));
                        }

                        /**
                         * 3. Adapter 생성 후 recyclerview에 지정
                         */
                        PackAdapter adapter = new PackAdapter(mDatas, recylerClickListener);
                        recyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<PackResultResult> call, Throwable t) {
                    Log.i("SOPT", "실패");
                    Toast.makeText(getApplicationContext(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT);

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "로그인을 해주세요.", Toast.LENGTH_SHORT);
        }
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

            String basketName = mDatas.get(position).basketName;
            String BasketUserName = mDatas.get(position).BasketUserName;
            String movieName = mDatas.get(position).movieName;
            int year = mDatas.get(position).year;
            String director = mDatas.get(position).director;
            int downCount = mDatas.get(position).downCount;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(MoviePackActivity.this, position + "번째 리사이클러뷰 항목 클릭!" + basketName + "/" + BasketUserName + "/" + movieName + "/" + year + "/" + director + "/" + downCount, Toast.LENGTH_SHORT).show();
        }
    };
}
