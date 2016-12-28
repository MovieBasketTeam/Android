package com.moviebasket.android.client.tag.hashtag;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.moviebasket.android.client.R;

import java.util.ArrayList;
import java.util.Random;

public class GridViewItem extends Activity {
    Activity act = this;
    GridView gridView;

    //이미지 배열 선언
    ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();

    //텍스트 배열 선언
    ArrayList<String> textArr = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        String totalRecomendation[] = {"#우울","#행복","#필주형","#서문","#비오는날","#맑음","#에어비앤비","#홍수빈인성","#ㅗㅗㅗ"};

        int total = totalRecomendation.length;
        //랜덤추출
        int a[] = new int[4];
        Random random = new Random();
        for (int i = 0 ; i < 4 ; i++)
        {
            a[i] = random.nextInt(total);
            for(int j=0 ; j<i ; j++)
            {
                if(a[i]==a[j])
                {
                    i--;
                }
            }
        }
        for(int k=0 ; k<4 ; k++)
        {
            textArr.add(totalRecomendation[a[k]]);
        }
        //


        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new gridAdapter());
        }


    public class gridAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public gridAdapter() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
        // TODO Auto-generated method stub
            return textArr.size();    //그리드뷰에 출력할 목록 수
        }

        @Override
        public Object getItem(int position) {
        // TODO Auto-generated method stub
            return textArr.get(position);    //아이템을 호출할 때 사용하는 메소드
        }

        @Override
        public long getItemId(int position) {
        // TODO Auto-generated method stub
            return position;    //아이템의 아이디를 구할 때 사용하는 메소드
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.search_grid_item, parent, false);
            }

            final Button btn =(Button)convertView.findViewById(R.id.textView1);

            btn.setText(textArr.get(position));
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                // TODO Auto-generated method stub
                //이미지를 터치했을때 동작하는 곳
                    Toast.makeText(getApplicationContext(),btn.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }
}