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
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.movie_detail.MovieDetailDialog;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.search.MovieSearchActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpecificBasketActivity extends AppCompatActivity {

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
        downBtn = (ImageView)findViewById(R.id.downBtn);
        downCount = (TextView)findViewById(R.id.downCount);


        Glide.with(getApplicationContext()).load(basketInfo.getExtras().getString("basket_image")).into(basketImg);
        basketName.setText(basketInfo.getExtras().getString("basket_name"));
        if ( basketInfo.getExtras().getInt("is_liked") == 0 ) {
            downBtn.setImageResource(R.drawable.sub_basket_nodown);
        } else {
            downBtn.setImageResource(R.drawable.sub_basket_down);
        }
        downCount.setText(String.valueOf(basket_count));

        downBtn.setOnClickListener(subClickListener);

/*        Log.i("Info : ", basketInfo.getExtras().getInt("basket_id")+"/"+basketInfo.getExtras().getString("basket_name")+"/"
                +basketInfo.getExtras().getString("basket_image")+"/"+basketInfo.getExtras().getInt("basket_like")+"/"+
        +basketInfo.getExtras().getInt("is_liked"));*/

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

    private View.OnClickListener subClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()){
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
                case R.id.downImg:
                    //담은 영화를 제거한 경우
                    AlertDialog.Builder packBuilder = new AlertDialog.Builder(v.getContext());
                    packBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    packBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(v.getContext(), "담은영화 제거했다고 치자", Toast.LENGTH_SHORT).show();
                        }
                    });
                    packBuilder.show();
                    break;
                case R.id.heartImg:
                    //추천한 영화를 제거한 경우
                    AlertDialog.Builder recBuilder = new AlertDialog.Builder(v.getContext());
                    recBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    recBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(v.getContext(), "추천한 영화 제거했다고 치자", Toast.LENGTH_SHORT).show();
                        }
                    });
                    recBuilder.show();
                    break;
            }
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

        adapter = new DetailAdapter(mDatas, recylerClickListener, subClickListener);
        recyclerView.setAdapter(adapter);

    }


}
