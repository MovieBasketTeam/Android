package com.moviebasket.android.client.mypage.movie_pack_list;

/**
 * Created by pilju on 2016-12-27.
 */

public class PackDatas {
    int image;
    String owner;
    String likecount;
    String title;
    String directer;
    String country;



    public PackDatas(int image, String owner, String likecount, String title, String directer, String country) {
        this.image = image;
        this.owner = owner;
        this.likecount = likecount;
        this.title = title;
        this.directer = directer;
        this.country = country;
    }
}
