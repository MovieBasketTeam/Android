package com.moviebasket.android.client.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    //스플래시 화면 추가
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler hd = new Handler(){
            public void handleMessage(Message msg){
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        hd.sendEmptyMessageDelayed(0, 3000);
    }
}