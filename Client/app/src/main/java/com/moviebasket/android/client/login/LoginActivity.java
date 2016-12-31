package com.moviebasket.android.client.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView eamil_x_btn;
    ImageView pass_x_btn;

    private MBService mbService;
    private boolean isLoginSuccess;
    private String member_email;
    private String member_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbService = ApplicationController.getInstance().getMbService();

        eamil_x_btn = (ImageView) findViewById(R.id.email_x);
        pass_x_btn = (ImageView) findViewById(R.id.pass_x);
        btn_login = (Button) findViewById(R.id.login);
        btn_signup = (Button) findViewById(R.id.signup);
        etxt_email = (EditText) findViewById(R.id.email);
        etxt_pw = (EditText) findViewById(R.id.password);

        eamil_x_btn.setOnClickListener(clickListener);
        pass_x_btn.setOnClickListener(clickListener);
        btn_login.setOnClickListener(clickListener);
        btn_signup.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //login 버튼 눌렀을 때
                case R.id.email_x:
                    etxt_email.setText("");
                    break;
                case R.id.pass_x:
                    etxt_pw.setText("");
                    break;
                case R.id.login:
                    //login을 위한 networking
                    member_email = etxt_email.getText().toString().trim();
                    member_pwd = etxt_pw.getText().toString().trim();
                    Log.i("LoginTest", "email : "+member_email+" , pwd : "+member_pwd);

                    final Call<LoginResult> getLoginResult = mbService.getLoginResult(member_email, member_pwd);
                    getLoginResult.enqueue(new Callback<LoginResult>() {
                        @Override
                        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                            if (response.isSuccessful()) {// 응답코드 200
                                Log.i("LoginTest", "요청메시지:"+call.toString()+" 응답메시지:"+response.toString());
                                LoginResult loginResult = response.body();
                                isLoginSuccess = loginResult.result.message.equals(SUCCESS)? true : false;
                                Log.i("LoginTest", "로그인 결과 : "+loginResult.result.message);
                            }
                            if(isLoginSuccess){
                                String Token = response.body().result.member_token;
                                ApplicationController.getInstance().savePreferences(Token);

                                Log.i("TOKEN", ApplicationController.getInstance().getPreferences());
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                Log.i("SOPT Token Test : ",  ApplicationController.getInstance().getPreferences());
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "이메일 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<LoginResult> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                            Log.i("LoginTest", "요청메시지:"+call.toString());
                        }
                    });
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
/*
    // 값 불러오기
    private void getPreferences(){
        SharedPreferences pref = getSharedPreferences(SecurityDataSet.STR_NAME, MODE_PRIVATE);
        String Token = pref.getString(SecurityDataSet.TK_KEY, "");
    }

    // 값 저장하기
    private void savePreferences(String Token){
        SharedPreferences pref = getSharedPreferences(SecurityDataSet.STR_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SecurityDataSet.TK_KEY, Token);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences(){
        SharedPreferences pref = getSharedPreferences(SecurityDataSet.STR_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(SecurityDataSet.TK_KEY);
        editor.commit();
    }
*/
}
