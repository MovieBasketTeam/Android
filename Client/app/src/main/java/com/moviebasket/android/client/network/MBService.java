package com.moviebasket.android.client.network;


import com.moviebasket.android.client.join.JoinResult;
import com.moviebasket.android.client.login.LoginResult;

import com.moviebasket.android.client.mypage.movie_rec_list.RecResult;
import com.moviebasket.android.client.mypage.movie_pack_list.PackResult;

import com.moviebasket.android.client.mypage.basket_list.BasketListDataResult;
import com.moviebasket.android.client.splash.VerifyLoginResult;
import com.moviebasket.android.client.splash.VerifyVersionResult;


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

    //로그인
    @FormUrlEncoded
    @POST("/member")
    Call<LoginResult> getLoginResult(@Field("member_email") String member_email, @Field("member_pwd") String member_pwd);

    //회원가입
    @FormUrlEncoded
    @POST("/member/signUp")
    Call<JoinResult> getJoinResult(@Field("member_name") String member_name, @Field("member_email") String member_email, @Field("member_pwd") String member_pwd);

    //추천한 영화 목록 리스트 가져오기
    @GET("/mypage/movie/recommend")
    Call<RecResult> getRecommendResult(@Header("member_token") String member_token );

    ////담은영화 리스트 가져오기
    @GET("/mypage/movie/cart")
    Call<PackResult> getMoviePackResult(@Header("member_token") String member_token);

    //바스켓리스트 가져오기 (sort값  1: 관리자 추천순 2: 날짜순 3: 인기순)
    @GET("/basket/")
    Call<BasketListDataResult> getBasketListDataResultOrderBy(@Header("member_token") String member_token, @Query("sort") int sort);

    //로그인 상태 확인하기
    @GET("/member/verify")
    Call<VerifyLoginResult> getVerifyLoginResult(@Header("member_token") String member_token);

    //연결 확인 (Connection)
    @GET("/member/version")
    Call<VerifyVersionResult> getVerifyVersionResult();

}
