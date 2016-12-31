package com.moviebasket.android.client.testpage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.moviebasket.android.client.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class JsoupActivity extends AppCompatActivity {

    TextView test_textview;
    String fullSummary;
    boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup);
        test_textview = (TextView)findViewById(R.id.test_textview);

        isRunning = true;

        getDataAsyncTask asyncTask = new getDataAsyncTask();
        asyncTask.execute();

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                np();
//            }
//        });
//        thread.start();
//        /*
//        try {
//            doc = Jsoup.connect(url)
//                    .header("content-type", "multipart/form-data; boundary=---011000010111000001101001")
//                    .header("authorization", "Basic ZmJfMTY2MTUzMDc0NzQ2MTk5NDox").header("cache-control", "no-cache")
//                    .get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        */
//
//        Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                test_textview.setText(fullSummary);
//            }
//        }, 2000);

    }


    public class getDataAsyncTask extends AsyncTask<String,Void,String> {

        public String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Document doc = null;
            String url = "http://movie.naver.com/movie/bi/mi/basic.nhn?code=67901";

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("ParsingTest", "run: IOException 오류남~~~ ");
            }

            Elements summary = doc.select("p.con_tx");
            fullSummary = "";

            Iterator it = summary.iterator();
            while (it.hasNext()) {
                fullSummary += it.next().toString();
            }
            isRunning = false;

            return fullSummary;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            test_textview.setText(s);

            super.onPostExecute(s);
        }
    }

}