package com.moviebasket.android.client.basket_detail;

/**
 * Created by user on 2016-12-28.
 */
public class DetailDatas {
    //    int movieImage;


    int movie_id;
    String movie_image;

//    String basketName;
    String movie_adder;
    String movie_title;
    int movie_pub_date;
    String movie_director;
    int movie_like;
    int is_liked;
    int is_cart;




    public DetailDatas(int movie_id, String movieImage, String BasketUserName, String movieName, int year, String director, int downCount, int heartImg, int downImg) {
        this.movie_id = movie_id;
        this.movie_image = movieImage;
//        this.basketName = basketName;
        this.movie_adder = BasketUserName;
        this.movie_title = movieName;
        this.movie_pub_date = year;
        this.movie_director = director;
        this.movie_like = downCount;
        this.is_liked = heartImg;
        this.is_cart = downImg;
    }

}
