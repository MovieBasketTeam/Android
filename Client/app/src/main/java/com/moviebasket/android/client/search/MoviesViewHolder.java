package com.moviebasket.android.client.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebasket.android.client.R;


/**
 * Created by pilju on 2016-12-30.
 */

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    ImageView movieImg;
    TextView title_year;
    TextView director_country;
    TextView scoreImg;

    public MoviesViewHolder(View itemView) {
        super(itemView);

        movieImg = (ImageView)itemView.findViewById(R.id.movieImg);
        title_year = (TextView)itemView.findViewById(R.id.title_year);
        director_country = (TextView)itemView.findViewById(R.id.director_country);
        scoreImg = (TextView)itemView.findViewById(R.id.scoreImg);

    }
    public ImageView getImageView(){
        return movieImg;
    }
}
