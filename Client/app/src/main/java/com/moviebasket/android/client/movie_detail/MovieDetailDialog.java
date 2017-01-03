package com.moviebasket.android.client.movie_detail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;
import com.moviebasket.android.client.search.MovieDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class MovieDetailDialog extends Dialog {

    //뷰 데이터
    TextView storybord;
    ImageView xbtn;
    MovieDetail detail;
    TextView txt_title;
    TextView txt_year;
    ImageView image_view_movie;
    TextView txt_director;
    TextView txt_actor;
    ImageView image_star_point;
    ImageView btn_more;

    //영화데이터
    String movie_title;
    String movie_link;
    String movie_image;
    String movie_pubDate;
    String movie_director;
    String movie_actor;
    String movie_userRating;
    String movie_summary;

    boolean isRunning;

    public MovieDetailDialog(Context context) {
        super(context);
    }

    public MovieDetailDialog(Context context, MovieDetail detail) {
        super(context);
        this.detail = detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_movie_detail);
        storybord = (TextView) findViewById(R.id.storybord);
        storybord.setMovementMethod(ScrollingMovementMethod.getInstance());

        //바깥영역 눌렀을 때 다이얼로그 종료
        this.setCanceledOnTouchOutside(true);

        if (detail == null) {
            Toast.makeText(getContext(), "영화 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        init();

        isRunning = true;

        initDataExceptSummary();
        loadSummary();

    }

    private void initDataExceptSummary() {
        movie_title = detail.title;
        movie_link = detail.link;
        movie_image = detail.image;
        movie_pubDate = detail.pubDate;
        movie_director = detail.director;
        movie_actor = detail.actor;
        movie_userRating = detail.userRating;

        txt_title.setText(movie_title);
        txt_year.setText(movie_pubDate);
        txt_director.setText(movie_director);
        txt_actor.setText(movie_actor);
        Glide.with(getContext()).load(movie_image).into(image_view_movie);

        float startPoint = Float.parseFloat(movie_userRating);
        int starNum = (int)(startPoint+0.5)/2;

        //평점에따라 영화별점 이미지 세팅
        switch(starNum){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie_link));
                getContext().startActivity(moreIntent);
            }
        });
    }

    private void loadSummary(){
        getSummaryAsyncTask asyncTask = new getSummaryAsyncTask();
        asyncTask.execute();
    }

    public class getSummaryAsyncTask extends AsyncTask<String,Void,String> {

        public String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Document doc = null;

            try {
                doc = Jsoup.connect(movie_link).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("ParsingTest", "run: IOException 오류남~~~ ");
            }

            Elements summary = doc.select("p.con_tx");
            movie_summary = "";

            Iterator it = summary.iterator();
            while (it.hasNext()) {
                movie_summary += it.next().toString();
            }
            isRunning = false;

            movie_summary = RemoveHTMLTag(movie_summary);
            return movie_summary;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            storybord.setText(s);
            super.onPostExecute(s);
        }
    }

    public String RemoveHTMLTag(String changeStr){
        if( changeStr != null && !changeStr.equals("") ) {
            changeStr = changeStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        } else {
            changeStr = "";
        }
        return changeStr;
    }

    private void init() {
        xbtn = (ImageView) findViewById(R.id.Xbtn);
        txt_title = (TextView) findViewById(R.id.title);
        txt_year = (TextView) findViewById(R.id.year);
        image_view_movie = (ImageView) findViewById(R.id.img_movie);
        txt_director = (TextView) findViewById(R.id.direct);
        txt_actor = (TextView) findViewById(R.id.actor);
        image_star_point = (ImageView) findViewById(R.id.starPoint);
        btn_more = (ImageView)findViewById(R.id.morebtn);

        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailDialog.this.dismiss();
            }
        });
    }
}
