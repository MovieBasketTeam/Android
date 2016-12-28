package com.moviebasket.android.client.mypage.movie_rec_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebasket.android.client.R;

/**
 * Created by pilju on 2016-12-28.
 */

public class RecViewHolder extends RecyclerView.ViewHolder {
    ImageView movie_image;
    TextView owner;
    TextView likecount;
    TextView title;
    TextView direct_country;
    ImageView book_mark;
    ImageView heart;


    public RecViewHolder(View itemView) {
        super(itemView);

        movie_image = (ImageView)itemView.findViewById(R.id.movie_image);
        owner = (TextView)itemView.findViewById(R.id.owner);
        likecount = (TextView)itemView.findViewById(R.id.likecount);
        title = (TextView)itemView.findViewById(R.id.title);
        direct_country = (TextView)itemView.findViewById(R.id.direct_country);
        book_mark = (ImageView)itemView.findViewById(R.id.book_mark);
        heart = (ImageView)itemView.findViewById(R.id.heart);
    }
}
