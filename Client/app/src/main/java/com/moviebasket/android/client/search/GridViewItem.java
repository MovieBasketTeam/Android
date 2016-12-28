package com.moviebasket.android.client.search;

import java.util.ArrayList;
import java.util.Random;

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
////

import com.moviebasket.android.client.R;

public class GridViewItem extends Activity {
    Activity act = this;
    GridView gridView1, gridView2, gridView3, gridView4;



    //이미지 배열 선언
    ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();

    //텍스트 배열 선언
    ArrayList<String> textArr = new ArrayList<String>();
    ArrayList<String> textArr2 = new ArrayList<String>();
    ArrayList<String> textArr3 = new ArrayList<String>();
    ArrayList<String> textArr4 = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        String totalRecomendation[] = {"#우울","#행복","#필주형","#서문","#비오는날","#맑음","#에어비앤비","#홍수빈인성","#ㅗㅗㅗ"};
        String theme1[] = {"#테마1","#테마2","#테마3","#테마4","#테마5","#테마6","#테마7","#테마8","#테마9"};
        String theme2[] = {"#테마11","#테마12","#테마13","#테마14","#테마15","#테마16","#테마17","#테마18","#테마19"};
        String theme3[] = {"#테마21","#테마22","#테마23","#테마24","#테마25","#테마26","#테마27","#테마28","#테마29"};

        int total = totalRecomendation.length;
        int theme1tot = theme1.length;
        int theme2tot = theme2.length;
        int theme3tot = theme3.length;


        //추천 랜덤추출
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


        gridView1 = (GridView) findViewById(R.id.gridView1);
        gridView1.setAdapter(new gridAdapter());

        /////theme1
        //추천 랜덤추출
        int b[] = new int[8];
        Random random1 = new Random();
        for (int i = 0 ; i < 8 ; i++)
        {
            b[i] = random1.nextInt(theme1tot);
            for(int j=0 ; j<i ; j++)
            {
                if(b[i]==b[j])
                {
                    i--;
                }
            }
        }
        for(int k=0 ; k<8 ; k++)
        {
            textArr2.add(theme1[b[k]]);
        }
        //


        gridView2 = (GridView) findViewById(R.id.gridView2);
        gridView2.setAdapter(new gridAdapter2());

/////////
        /////theme2
        //추천 랜덤추출
        int c[] = new int[4];
        Random random2 = new Random();
        for (int i = 0 ; i < 4 ; i++)
        {
            c[i] = random2.nextInt(theme2tot);
            for(int j=0 ; j<i ; j++)
            {
                if(c[i]==c[j])
                {
                    i--;
                }
            }
        }
        for(int k=0 ; k<4 ; k++)
        {
            textArr3.add(theme2[c[k]]);
        }
        //


        gridView3 = (GridView) findViewById(R.id.gridView3);
        gridView3.setAdapter(new gridAdapter3());

/////////

        /////theme3
        //추천 랜덤추출
        int d[] = new int[8];
        Random random3 = new Random();
        for (int i = 0 ; i < 8 ; i++)
        {
            d[i] = random3.nextInt(theme3tot);
            for(int j=0 ; j<i ; j++)
            {
                if(d[i]==d[j])
                {
                    i--;
                }
            }
        }
        for(int k=0 ; k<8 ; k++)
        {
            textArr4.add(theme3[d[k]]);
        }
        //


        gridView4 = (GridView) findViewById(R.id.gridView4);
        gridView4.setAdapter(new gridAdapter4());

/////////
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

            final Button btn =(Button)convertView.findViewById(R.id.gridItem);

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

    ////그리드2(theme1)
    public class gridAdapter2 extends BaseAdapter {
        LayoutInflater inflater;

        public gridAdapter2() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return textArr2.size();    //그리드뷰에 출력할 목록 수

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return textArr2.get(position);    //아이템을 호출할 때 사용하는 메소드
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

            final Button btn2 =(Button)convertView.findViewById(R.id.gridItem);

            btn2.setText(textArr2.get(position));
            btn2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //이미지를 터치했을때 동작하는 곳
                    Toast.makeText(getApplicationContext(),btn2.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }

    ////그리드2(theme2)
    public class gridAdapter3 extends BaseAdapter {
        LayoutInflater inflater;

        public gridAdapter3() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return textArr3.size();    //그리드뷰에 출력할 목록 수

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return textArr3.get(position);    //아이템을 호출할 때 사용하는 메소드
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

            final Button btn3 =(Button)convertView.findViewById(R.id.gridItem);

            btn3.setText(textArr3.get(position));
            btn3.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //이미지를 터치했을때 동작하는 곳
                    Toast.makeText(getApplicationContext(),btn3.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }

    ////그리드2(theme3)
    public class gridAdapter4 extends BaseAdapter {
        LayoutInflater inflater;

        public gridAdapter4() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return textArr4.size();    //그리드뷰에 출력할 목록 수

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return textArr4.get(position);    //아이템을 호출할 때 사용하는 메소드
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

            final Button btn4 =(Button)convertView.findViewById(R.id.gridItem);

            btn4.setText(textArr4.get(position));
            btn4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //이미지를 터치했을때 동작하는 곳
                    Toast.makeText(getApplicationContext(),btn4.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }
}