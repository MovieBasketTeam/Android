package com.moviebasket.android.client.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;

import java.util.ArrayList;


/**
 * Created by pilju on 2016-12-30.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder>{

    ArrayList<MoviesDatas> mDatas;
    private ViewGroup parent;
    private View itemView;
    private static final int FOOTER_VIEW = 1;
    public MoviesAdapter(ArrayList<MoviesDatas> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;
        if (viewType == FOOTER_VIEW) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_moviesearch, parent, false);

            MoviesViewHolder vh = new MoviesViewHolder(itemView);

            return vh;
        }

        // 뷰홀더 패턴을 생성하는 메소드.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_moviesearch, parent, false);
        MoviesViewHolder viewHolder = new MoviesViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        Glide.with(parent.getContext()).load(mDatas.get(position).movieImg).into(holder.getImageView());
        holder.title_year.setText(mDatas.get(position).title+"("+mDatas.get(position).year+")");
        holder.director_country.setText(mDatas.get(position).director+"/"+mDatas.get(position).country);
        holder.scoreImg.setText(mDatas.get(position).scoreImg);

    }

    @Override
    public int getItemCount() {
        return (mDatas != null) ? mDatas.size() : 0;
    }
}
