
package com.moviebasket.android.client.mypage.movie_pack_list;

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

public class MoviePackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PackDetail> packdetail;
    LinearLayoutManager mLayoutManager;
    private MBService mbService;

    PackResultResult result;
    PackAdapter adapter;
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
        packdetail = new ArrayList<>();

        adapter = new PackAdapter(packdetail, recylerClickListener, subClickListener);
        recyclerView.setAdapter(adapter);

        // LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        /**
         * 2. recyclerview에 보여줄 data
         */
        String token = ApplicationController.getInstance().getPreferences();
        if (!token.equals("")) {
            Call<PackResultResult> getMovieData = mbService.getMoviePackResult(token);
            getMovieData.enqueue(new Callback<PackResultResult>() {
                @Override
                public void onResponse(Call<PackResultResult> call, Response<PackResultResult> response) {
                    if (response.isSuccessful()) {
                        result = response.body();

                        packdetail.addAll(result.result.result);
                        Log.i("NetConfirm", "PackDetails 들어온 데이터들 : "+packdetail.toString());

                        /**
                         * 3. Adapter 생성 후 recyclerview에 지정
                         */

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


        //
    }

    private View.OnClickListener recylerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //1.리사이클러뷰에 몇번째 항목을 클릭했는지 그 position을 가져오는 것.
            int position = recyclerView.getChildLayoutPosition(v);
            //2.position번째 항목의 Data를 가져오는 방법

            //3.여기서부터는 각자 알아서 처리해야할 것을 코딩해야함.
            //ex) 충민: 바스켓 리스트를 누르면 그 항목의 바스켓 상세페이지로 이동시켜야함.
            //Intent BasketDetailIntent = new Intent(MainActivity.this, )
            Toast.makeText(MoviePackActivity.this, position + "번째 리사이클러뷰 항목 클릭!" , Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener subClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()){
                case R.id.removeImg:
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
}
