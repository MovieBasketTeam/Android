package com.moviebasket.android.client.mypage.movie_rec_list;

/**
 * Created by pilju on 2016-12-28.
 */

public class RecDatas {

    int movie_image;
    int movie_id;
    String owner;
    String movie_title;
    String movie_director;
    String movie_pub_date;
    String movie_user_rating;
    String movie_link;
    String movie_like;
    int book_mark;
    int is_liked;
    String message;

    public RecDatas(int movie_image, int movie_id, String owner, String movie_title, String movie_director,
            String movie_pub_date, String movie_user_rating, String movie_link, String movie_like, int book_mark, int is_liked, String message)
    {
        this.movie_image = movie_image;
        this.movie_id = movie_id;
        this.owner = owner;
        this.movie_title = movie_title;
        this.movie_director = movie_director;
        this.movie_pub_date = movie_pub_date;
        this.movie_user_rating = movie_user_rating;
        this.movie_link = movie_link;
        this.movie_like = movie_like;
        this.book_mark = book_mark;
        this.is_liked = is_liked;
        this.message = message;

    }
}
