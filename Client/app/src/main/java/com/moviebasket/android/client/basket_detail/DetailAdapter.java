package com.moviebasket.android.client.basket_detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviebasket.android.client.R;

import java.util.ArrayList;

/**
 * Created by user on 2016-12-28.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    ArrayList<DetailDatas> mDatas;

    public DetailAdapter() {

    }

    public DetailAdapter(ArrayList<DetailDatas> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 뷰홀더 패턴을 생성하는 메소드.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_detail, parent, false);
        DetailViewHolder viewHolder = new DetailViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        //리싸이클뷰에 항목을 뿌려주는 메소드.
        holder.ranking_image.setImageResource(mDatas.get(position).ranking);
        holder.movie_image.setImageResource(mDatas.get(position).image);
        holder.owner.setText(mDatas.get(position).owner);
        holder.title_year.setText(mDatas.get(position).title + "(" + mDatas.get(position).year + ")");
        holder.direct_country.setText(mDatas.get(position).directer + "/" + mDatas.get(position).country);
        holder.download.setImageResource(mDatas.get(position).download);
        holder.heart.setImageResource(mDatas.get(position).heart);
        holder.likecount.setText(mDatas.get(position).likecount);

    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}