package com.moviebasket.android.client.basket_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebasket.android.client.R;

/**
 * Created by user on 2016-12-28.
 */
public class DetailViewHolder extends RecyclerView.ViewHolder {
    ImageView ranking_image;
    ImageView movie_image;
    TextView owner;
    TextView title_year;
    TextView direct_country;
    ImageView download;
    ImageView heart;
    TextView likecount;


    public DetailViewHolder(View itemView) {
        super(itemView);

        ranking_image = (ImageView)itemView.findViewById(R.id.ranking_image);
        movie_image = (ImageView)itemView.findViewById(R.id.movie_image);
        owner = (TextView)itemView.findViewById(R.id.owner);
        title_year = (TextView)itemView.findViewById(R.id.title_year);
        direct_country = (TextView)itemView.findViewById(R.id.direct_country);
        download = (ImageView)itemView.findViewById(R.id.download);
        heart = (ImageView)itemView.findViewById(R.id.heart);
        likecount = (TextView)itemView.findViewById(R.id.likecount);
    }
}
