package com.moviebasket.android.client.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.join.JoinActivity;
import com.moviebasket.android.client.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //충민: 임시로 엑티비티끼리 연결하기 위한것.
        btn_login = (Button)findViewById(R.id.login);
        btn_signup = (Button)findViewById(R.id.signup);
        btn_login.setOnClickListener(clickListener);
        btn_signup.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //login 버튼 눌렀을 때
                case R.id.login:
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                    break;
                //signup 버튼 눌렀을 때
                case R.id.signup:
                    Intent signUpIntent = new Intent(LoginActivity.this, JoinActivity.class);
                    startActivity(signUpIntent);
                    //사실은 startActivityForResult로 인텐트보내야함.
                   // finish();
                    break;
            }
        }
    };
}
