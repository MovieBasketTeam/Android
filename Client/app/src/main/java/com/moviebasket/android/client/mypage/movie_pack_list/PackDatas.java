package com.moviebasket.android.client.mypage.movie_pack_list;

/**
 * Created by pilju on 2016-12-27.
 */

public class PackDatas {
    int image;
    String owner;
    int likecount;
    String title;
    String directer;
    String country;
    String bookmark;
    String heart;


    public PackDatas(int image, String owner, int likecount, String title, String directer, String country, String bookmark, String heart) {
        this.image = image;
        this.owner = owner;
        this.likecount = likecount;
        this.title = title;
        this.directer = directer;
        this.country = country;
        this.bookmark = bookmark;
        this.heart = heart;
    }
}
