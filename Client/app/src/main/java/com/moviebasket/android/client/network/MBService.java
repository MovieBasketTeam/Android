package com.moviebasket.android.client.network;

import com.moviebasket.android.client.login.LoginResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by LEECM on 2016-12-28.
 */

public interface MBService {
    @FormUrlEncoded
    @POST("/member")
    Call<LoginResult> getLoginResult(@Field("member_email") String member_email, @Field("member_pwd") String member_pwd);
}
