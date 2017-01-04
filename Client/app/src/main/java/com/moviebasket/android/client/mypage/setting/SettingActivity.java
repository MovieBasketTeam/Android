package com.moviebasket.android.client.mypage.setting;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.splash.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private MBService mbService;
    private boolean isResponseSuccess;
    private static final String FAILURE = "session error";
    private String token;
    ImageView userimage;
    TextView username;
    TextView useremail;
    RelativeLayout btn_withdraw;

    private static final int PICK_FROM_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mbService = ApplicationController.getInstance().getMbService();

        userimage = (ImageView) findViewById(R.id.userimage1);
        username = (TextView) findViewById(R.id.username);
        useremail = (TextView) findViewById(R.id.useremail);
        btn_withdraw = (RelativeLayout) findViewById(R.id.btn_withdraw);
        token = ApplicationController.getInstance().getPreferences();

        //탈퇴하기 버튼 클릭시 탈퇴하겠냐고 확인
        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("정말로 무비바스켓을 떠나실건가요?");
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //탈퇴 요청하기.
                        Call<MemberWithdrawResult> verifyMemberWithdraw = mbService.verifyMemberWithdraw(token);
                        verifyMemberWithdraw.enqueue(new Callback<MemberWithdrawResult>() {
                            @Override
                            public void onResponse(Call<MemberWithdrawResult> call, Response<MemberWithdrawResult> response) {
                                if(response.isSuccessful()){
                                    String message = response.body().result.message;
                                    if(message.equals("withdraw success")){
                                        ApplicationController.getInstance().savePreferences("");
                                        Intent withdrawIntent = new Intent(SettingActivity.this, SplashActivity.class);
                                        withdrawIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(withdrawIntent);
                                        finish();
                                    }else{
                                        Toast.makeText(SettingActivity.this, "회원탈퇴 실패", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<MemberWithdrawResult> call, Throwable t) {
                                Toast.makeText(SettingActivity.this, "서버와 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.show();

            }
        });
        ////통신보안
        String token = ApplicationController.getInstance().getPreferences();
        Log.i("NetConfirm", "token: " + token);

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
        ////

        userimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Gallery 호출
                //intent.setType("Camera/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                // 잘라내기 셋팅
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {

        if (requestCode == PICK_FROM_GALLERY) {
            if(data==null)
                return;
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo = extras2.getParcelable("data");
                userimage.setImageBitmap(photo);
            }
        }

    }



}
