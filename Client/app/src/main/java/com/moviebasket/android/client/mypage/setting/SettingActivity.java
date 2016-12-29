package com.moviebasket.android.client.mypage.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.main.MainActivity;

public class SettingActivity extends AppCompatActivity {

    ImageView backbutton;
    ImageView userimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backbutton = (ImageView) findViewById(R.id.backbutton);
        userimage = (ImageView) findViewById(R.id. userimage1);
        backbutton.setOnClickListener(clickListener);
        userimage.setOnClickListener(clickListener);

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
