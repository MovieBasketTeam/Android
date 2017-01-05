package com.moviebasket.android.client.mypage.setting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;
import com.moviebasket.android.client.global.ApplicationController;
import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.splash.SplashActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_IMAGE = 1001;

    private MBService mbService;
    private boolean isResponseSuccess;
    private static final String FAILURE = "session error";
    private String token;
    CircleImageView userimage;
    TextView username;
    TextView useremail;
    RelativeLayout btn_withdraw;
    private ProgressDialog mProgressDialog;
    private static final int PICK_FROM_GALLERY = 2;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mbService = ApplicationController.getInstance().getMbService();

        userimage = (CircleImageView) findViewById(R.id.userimage1);
        username = (TextView) findViewById(R.id.username);
        useremail = (TextView) findViewById(R.id.useremail);
        btn_withdraw = (RelativeLayout) findViewById(R.id.btn_withdraw);
        token = ApplicationController.getInstance().getPreferences();

        mProgressDialog = new ProgressDialog(SettingActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("처리중..");
        mProgressDialog.setIndeterminate(true);

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

        //유저의 개인정보 가져오기.
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
                    if(!(settingResult.result.member_image==null || settingResult.result.member_image.equals(""))){
                        Glide.with(SettingActivity.this).load(String.valueOf(settingResult.result.member_image)).into(userimage);
                    }
                }
            }
            @Override
            public void onFailure(Call<SettingResult> call, Throwable t) {
                Toast.makeText(SettingActivity.this, "서비스에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
                Log.i("recommendMovie Test", "요청메시지:" + call.toString());
            }

        });


//
//        //유저의 프로필 사진 만들기. (요청하기도 포함)
//        userimage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // 사진 갤러리 호출
//                // 아래의 코드는 스마트폰의 앨범에서 이미지를 가져오는 부분입니다.
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.putExtra("crop", "true");
//                intent.putExtra("aspectX", 0);
//                intent.putExtra("aspectY", 0);
//                intent.putExtra("outputX", 200);
//                intent.putExtra("outputY", 150);
//                startActivityForResult(intent, REQUEST_CODE_FOR_IMAGE);
//
//
//                mProgressDialog.show();
//
//
//                // TODO: 2016. 11. 21. 등록하기.
//
//                /**
//                 * 서버로 보낼 파일의 전체 url을 이용해 작업
//                 */
//
//                // 먼저, 게시판의 제목과 내용을 body에 담아서 보내기위해 작업.
//                RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), inputTitleEdit.getText().toString());
//                RequestBody content = RequestBody.create(MediaType.parse("multipart/form-data"), inputContentEdit.getText().toString());
//                RequestBody writer = RequestBody.create(MediaType.parse("multipart/form-data"), inputWriterEdit.getText().toString());
//
//                //body가 실질적으로 담을 부분
//                MultipartBody.Part body;;
//
//                if(imgUrl==""){
//                    body = null;
//                }
//                else{
//
//                    /**
//                     * 비트맵 관련한 자료는 아래의 링크에서 참고
//                     * http://mainia.tistory.com/468
//                     */
//                    /**
//                     * 아래의 부분은 이미지 리사이징하는 부분입니다
//                     * 왜?? 이미지를 리사이징해서 보낼까요?
//                     * 안드로이드는 기본적으로 JVM Heap Memory가 얼마되지 않습니다.
//                     * 구글에서는 최소 16MByte로 정하고 있으나, 제조사 별로 디바이스별로 Heap영역의 크기는 다르게 정하여 사용하고 있습니다.
//                     * 또한, 이미지를 서버에 업로드할 때 이미지크기가 크면 그만큼 시간이 걸리고 데이터 소모가 되겠죠!
//                     */
//                    BitmapFactory.Options options = new BitmapFactory.Options();
////                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다
//                    //이거는 .compress() 같이 쓰이면 중복됨. 그러므로 둘 중 하나만 쓴다.
//
//                    InputStream in = null; // here, you need to get your context.
//                    try {
//                        in = getContentResolver().openInputStream(data);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos); // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ),
//
//
//                    RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
//
//                    File photo = new File(imgUrl); // 그저 블러온 파일의 이름을 알아내려고 사용..
//
//                    // MultipartBody.Part is used to send also the actual file writer
//                    body = MultipartBody.Part.createFormData("image", photo.getName(), photoBody);
//                    //사실 서버측에서는 이미지의 이름을 재조정하기때문에 photo.getName()은 의미가 없다.
//
//                }
//
//
//                // TODO: 2016. 11. 21. 등록 요청
//                Call<RegisterResult> requestRegister = service.requestRegister(body, title, content, writer);
//                requestRegister.enqueue(new Callback<RegisterResult>() {
//                    @Override
//                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
//                        if(response.body().result.equals("create")){
//                            Toast.makeText(RegisterActivity.this, "등록성공", Toast.LENGTH_SHORT).show();
//                            mProgressDialog.dismiss();
//                            finish();
//                        }else{
//                            Toast.makeText(RegisterActivity.this, "등록실패", Toast.LENGTH_SHORT).show();
//                            mProgressDialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<RegisterResult> call, Throwable t) {
//
//                    }
//                });
//                // 등록요청
//                Call<UpdateProfileImageResult> updateProfileImageResult = mbService.updateProfileImage(token, body);
//                updateProfileImageResult.enqueue(new Callback<UpdateProfileImageResult>() {
//                    @Override
//                    public void onResponse(Call<UpdateProfileImageResult> call, Response<UpdateProfileImageResult> response) {
//                        if (response.isSuccessful()) {
//                            UpdateProfileImageResult result = response.body();
//                            if(result.result.message.equals("upload profile success")){
//                                Toast.makeText(SettingActivity.this, "프로필 변경 성공!", Toast.LENGTH_SHORT).show();
//                                mProgressDialog.dismiss();
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UpdateProfileImageResult> call, Throwable t) {
//                        mProgressDialog.dismiss();
//                        Toast.makeText(SettingActivity.this, "서버와 연결을 확인하세요", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==REQUEST_CODE_FOR_IMAGE){
//
//                //이미지를 성공적으로 가져왔을 경우
//                if(resultCode== Activity.RESULT_OK)
//                {
//                    try {
//                        //Uri에서 이미지 이름을 얻어온다.
//                        String name_Str = getImageNameToUri(data.getData());
//                        imgNameTextView.setText(name_Str);
//                        this.data = data.getData();
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//                else{
//                    imgUrl = "";
//                    imgNameTextView.setText("");
//                }
//        }
//    }
}
