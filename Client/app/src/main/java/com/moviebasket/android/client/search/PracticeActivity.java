package com.moviebasket.android.client.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moviebasket.android.client.R;

public class PracticeActivity extends AppCompatActivity {

    Button btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        btn = (Button)findViewById(R.id.practice_btn);
        txt = (TextView) findViewById(R.id.practice_textview);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기다 네트워킹을 실습하기!
                // 버튼을 누르면, TextView에다가 서버에서 준 요청메세지를 띄우기!.
            }
        });
    }
}
