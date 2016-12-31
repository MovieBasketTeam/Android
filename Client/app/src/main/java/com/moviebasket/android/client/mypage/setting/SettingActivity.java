package com.moviebasket.android.client.mypage.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.main.MainActivity;
import com.moviebasket.android.client.network.MBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private MBService mbService;
    private boolean isResponseSuccess;
    private static final String FAILURE = "session error";
    ImageView userimage;
    TextView username;
    TextView useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mbService = ApplicationController.getInstance().getMbService();

        userimage = (ImageView) findViewById(R.id. userimage1);
        username = (TextView) findViewById(R.id. username);
        useremail = (TextView) findViewById(R.id. useremail);
        userimage.setOnClickListener(clickListener);

        String token = ApplicationController.getInstance().getPreferences();

        Log.i("NetConfirm", "token: "+token);

        Call<SettingResult> getSettingResult = mbService.getSettingResult(token);
        getSettingResult.enqueue(new Callback<SettingResult>() {
            @Override
            public void onResponse(Call<SettingResult> call, Response<SettingResult> response) {
                SettingResult settingResult = response.body();
                if (response.isSuccessful()) {// 응답코드 200
                    Log.i("recommendMovie Test", "요청메시지:" + call.toString() + " 응답메시지:" + response.toString());
                    isResponseSuccess = settingResult.result.message==null ? true : false;
                    Log.i("recommendMovie Test", "응답 결과 : " + isResponseSuccess);
                }
                if (isResponseSuccess) {
                    username.setText(String.valueOf(settingResult.result.member_name));
                    useremail.setText(String.valueOf(settingResult.result.member_email));
                    }
                }
            @Override
            public void onFailure(Call<SettingResult> call, Throwable t) {
                Toast.makeText(SettingActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.i("recommendMovie Test", "요청메시지:" + call.toString());
            }

        });

    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
               case R.id.backbutton:
                   Intent BackIntent = new Intent(SettingActivity.this, MainActivity.class);
                   startActivity(BackIntent);
                   finish();
                   break;
                case R.id.userimage1:
                    Toast.makeText(SettingActivity.this, "사진 바꾸기 준비중", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
