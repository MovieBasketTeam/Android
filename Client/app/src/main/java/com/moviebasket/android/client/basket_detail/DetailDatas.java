package com.moviebasket.android.client.basket_detail;

/**
 * Created by user on 2016-12-28.
 */
public class DetailDatas {
    int ranking;
    int image;
    String owner;

    String title;
    String year;
    String directer;
    String country;
    int download;
    int heart;
    String likecount;

    public DetailDatas(int ranking, int image, String owner, String title, String year, String directer, String country, int download, int heart, String likecount) {
        this.ranking = ranking;
        this.image = image;
        this.owner = owner;
        this.likecount = likecount;
        this.title = title;
        this.directer = directer;
        this.country = country;
        this.year = year;
        this.download = download;
        this.heart = heart;
    }

}
