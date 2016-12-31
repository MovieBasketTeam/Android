package com.moviebasket.android.client.mypage.movie_pack_list;

/**
 * Created by pilju on 2016-12-31.
 */

public class PackDetail {

    String movie_id; //인덱스
    String movie_title; //영화이름
    String movie_image; //영화이미지 URL
    String movie_director; //감독
    String movie_pub_date; //연도
    String movie_movie_adder ; // 바스캇에담은사람이름
    String movie_user_rating; //평점
    String movie_link; //영화링크
    int movie_like; // 좋아요 갯수
    int is_liked; // 좋아요정보 : 1, 0
    int is_cart; //담은정보 : 1,0
    String basket_name; // 바스켓이름

}
