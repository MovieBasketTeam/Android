package com.moviebasket.android.client.mypage.movie_pack_list;

/**
 * Created by pilju on 2016-12-27.
 */

public class PackDatas {
//    int movieImage;
    String movieImage;

    String basketName;
    String BasketUserName;
    String movieName;
    String year;
    String director;
    int downCount;
    int heartImg;
    int downImg;




    public PackDatas(String movieImage, String basketName, String BasketUserName, String movieName, String year, String director, int downCount, int heartImg, int downImg) {
        this.movieImage = movieImage;
        this.basketName = basketName;
        this.BasketUserName = BasketUserName;
        this.movieName = movieName;
        this.year = year;
        this.director = director;
        this.downCount = downCount;
        this.heartImg = heartImg;
        this.downImg = downImg;
    }
}
