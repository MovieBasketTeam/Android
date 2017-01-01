package com.moviebasket.android.client.tag.tagged;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.mypage.basket_list.BasketListAdapter;
import com.moviebasket.android.client.mypage.basket_list.BasketListDatas;

import java.util.ArrayList;

import static com.moviebasket.android.client.R.id.basketName;
import static com.moviebasket.android.client.R.id.downCount;

public class TaggedBasketListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BasketListDatas> mDatas = new ArrayList<BasketListDatas>();

    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagged_basket_list);

        //search_list에서 누른 버튼의 텍스트를 불러옴
        TextView title =(TextView)findViewById(R.id.hash_tag_title);
        Intent intent = getIntent();
        String tag_title = intent.getStringExtra("hash_title");
        title.setText(tag_title);
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
        mDatas = new ArrayList<BasketListDatas>();

        //여기는 네이버 api에서 정보 받아와서 for문으로 돌려서 add해야될것같아요 임시로 넣어둠!!
        // mDatas.add(new BasketListDatas(R.drawable.basket_img, R.drawable.text_image, "이필주", R.drawable.down_btn, "1,882"));
        //   mDatas.add(new BasketListDatas(R.drawable.basket_img, R.drawable.text_image, "김지원", R.drawable.down_btn, "2,647"));
        //  mDatas.add(new BasketListDatas(R.drawable.basket_img, R.drawable.text_image, "최서문", R.drawable.down_btn, "822"));
        //   mDatas.add(new BasketListDatas(R.drawable.basket_img, R.drawable.text_image, "이충민", R.drawable.down_btn, "3,236"));
        //   mDatas.add(new BasketListDatas(R.drawable.basket_img, R.drawable.text_image, "권민하", R.drawable.down_btn, "7,457"));

        /**
         * 3. Adapter 생성 후 recyclerview에 지정
         */
        BasketListAdapter adapter = new BasketListAdapter(mDatas, recylerClickListener, subClickListener);

        recyclerView.setAdapter(adapter);
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
            Toast.makeText(TaggedBasketListActivity.this, position + "번째 리사이클러뷰 항목 클릭!" + basketName + "/" + downCount, Toast.LENGTH_SHORT).show();
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
            }
        }
    };
}

