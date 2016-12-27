package com.moviebasket.android.client.mypage.movie_pack_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebasket.android.client.R;

public class PackViewHolder extends RecyclerView.ViewHolder {

    ImageView movie_image;
    TextView owner;
    TextView likecount;
    TextView title;
    TextView direct_country;


    public PackViewHolder(View itemView) {
        super(itemView);

        movie_image = (ImageView)itemView.findViewById(R.id.movie_image);
        owner = (TextView)itemView.findViewById(R.id.owner);
        likecount = (TextView)itemView.findViewById(R.id.likecount);
        title = (TextView)itemView.findViewById(R.id.title);
        direct_country = (TextView)itemView.findViewById(R.id.direct_country);
    }
}
