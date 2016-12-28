package com.moviebasket.android.client.mypage.movie_rec_list;

/**
 * Created by pilju on 2016-12-28.
 */

public class RecDatas {

    int image;
    String owner;
    String likecount;
    String title;
    String directer;
    String country;
    int book_mark;
    int heart;

    public RecDatas(int image, String owner, String likecount, String title, String directer, String country, int book_mark, int heart) {
        this.image = image;
        this.owner = owner;
        this.likecount = likecount;
        this.title = title;
        this.directer = directer;
        this.country = country;
        this.book_mark = book_mark;
        this.heart = heart;
    }
}
