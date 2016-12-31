package com.moviebasket.android.client.network;


import com.moviebasket.android.client.join.JoinResult;
import com.moviebasket.android.client.login.LoginResult;
import com.moviebasket.android.client.mypage.movie_pack_list.PackResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by LEECM on 2016-12-28.
 */

public interface MBService {


    @FormUrlEncoded
    @POST("/member")
    Call<LoginResult> getLoginResult(@Field("member_email") String member_email, @Field("member_pwd") String member_pwd);

    @FormUrlEncoded
    @POST("/member/signUp")
    Call<JoinResult> getJoinResult(@Field("member_name") String member_name, @Field("member_email") String member_email, @Field("member_pwd") String member_pwd);

    @GET("/mypage/movie/cart")
    Call<PackResult> getMoviePackResult(@Header("member_token") String member_token);

}
