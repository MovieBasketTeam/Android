package com.moviebasket.android.client.mypage.movie_rec_list;

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

public class MovieRecActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<RecDatas> mDatas = new ArrayList<RecDatas>();

    private MBService mbService;
    private boolean isResponseSuccess;
    private static final String FAILURE = "view failed";


    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rec);

        /**
         * 1. recyclerview 초기화
         */
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerview);
        mbService = ApplicationController.getInstance().getMbService();
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
        mDatas = new ArrayList<RecDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!
        //7,8번째 파라미터는 if문으로 값이 0,1 일때 각각 다른이미지가 뜨도록 해야함!!

        //mDatas.add(new RecDatas(R.drawable.fab_add, "owner2", "1005", "영화", "이충민", "한국", R.drawable.back, R.drawable.back));
        //mDatas.add(new RecDatas(R.drawable.down_btn, "owner3", "1006", "신도림", "최서문", "미국", R.mipmap.ic_launcher, R.drawable.back));
        //mDatas.add(new RecDatas(R.drawable.menu_button_drawer, "owner4", "1004", "노블래스", "너구리", "미국", R.drawable.back, R.mipmap.ic_launcher));
        //mDatas.add(new RecDatas(R.drawable.back, "owner5", "2004", "대가리", "그냥너구리", "미국", R.mipmap.ic_launcher, R.drawable.fab_add));

        String token = ApplicationController.getInstance().getMember_token();

        Log.i("NetConfirm", "token: "+token);

            Call<RecResultParent> getRecommendResult = mbService.getRecommendResult(token);
            getRecommendResult.enqueue(new Callback<RecResultParent>() {
                @Override
                public void onResponse(Call<RecResultParent> call, Response<RecResultParent> response) {
                    RecResultParent recResult = response.body();
                    if (response.isSuccessful()) {// 응답코드 200
                        Log.i("recommendMovie Test", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                        isResponseSuccess = recResult.result.result.get(0).message.equals(FAILURE) ? false : true;
                        Log.i("recommendMovie Test", "응답 결과 : " + isResponseSuccess);
                    }
                    if (isResponseSuccess) {

                        for (int i = 0; i < recResult.result.result.size(); i++) {
                            mDatas.add(new RecDatas(recResult.result.result.get(i).movie_image,
                                    recResult.result.result.get(i).movie_id,
                                    recResult.result.result.get(i).owner,
                                    recResult.result.result.get(i).movie_title,
                                    recResult.result.result.get(i).movie_director,
                                    recResult.result.result.get(i).movie_pub_date,
                                    recResult.result.result.get(i).movie_user_rating,
                                    recResult.result.result.get(i).movie_link,
                                    recResult.result.result.get(i).movie_like,
                                    recResult.result.result.get(i).book_mark,
                                    recResult.result.result.get(i).is_liked,
                                    recResult.result.result.get(i).message));
                        }
                    }
                }

                @Override
                public void onFailure(Call<RecResultParent> call, Throwable t) {
                    Toast.makeText(MovieRecActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                    Log.i("recommendMovie Test", "요청메시지:" + call.toString());
                }
            });


        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        RecAdapter adapter = new RecAdapter(mDatas, recylerClickListener);
        //basketListAdapter = new BasketListAdapter(basketListDatases, recylerClickListener);
        recyclerView.setAdapter(adapter);

    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법
            String MovieTitle = mDatas.get(position).movie_title;

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            //Toast.makeText(MovieSearchActivity.this, position+"번째 리사이클러뷰 항목 클릭!", Toast.LENGTH_SHORT).show();
            Toast.makeText(MovieRecActivity.this, MovieTitle, Toast.LENGTH_SHORT).show();
        }
    };
}
