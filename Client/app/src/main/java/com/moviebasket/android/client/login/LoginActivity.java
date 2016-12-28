package com.moviebasket.android.client.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.join.JoinActivity;
import com.moviebasket.android.client.main.MainActivity;
import com.moviebasket.android.client.network.MBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String SUCCESS = "login success";
    private static final String FAILURE = "check information";

    Button btn_login;
    Button btn_signup;
    EditText etxt_email;
    EditText etxt_pw;

    private MBService mbService;
    private boolean isLoginSuccess;
    private String member_email;
    private String member_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbService = ApplicationController.getInstance().getMbService();

        btn_login = (Button) findViewById(R.id.login);
        btn_signup = (Button) findViewById(R.id.signup);
        etxt_email = (EditText) findViewById(R.id.email);
        etxt_pw = (EditText) findViewById(R.id.password);

        btn_login.setOnClickListener(clickListener);
        btn_signup.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //login 버튼 눌렀을 때
                case R.id.login:
                    //login을 위한 networking
                    member_email = etxt_email.getText().toString().trim();
                    member_pwd = etxt_pw.getText().toString().trim();
                    Log.i("LoginTest", "email : "+member_email+" , pwd : "+member_pwd);
                    Call<LoginResult> getLoginResult = mbService.getLoginResult(member_email, member_pwd);
                    getLoginResult.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                            if (response.isSuccessful()) {// 응답코드 200
                                Log.i("LoginTest", "요청메시지:"+call.toString()+" 응답메시지:"+response.toString());
                                LoginResult loginResult = response.body();
                                isLoginSuccess = loginResult.result.message.equals(SUCCESS)? true : false;
                                Log.i("LoginTest", "로그인 결과 : "+loginResult.result.message);
                            }

                        }
                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                            Log.i("LoginTest", "요청메시지:"+call.toString());
                        }
                    });

                    if(isLoginSuccess){
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "이메일 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
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
