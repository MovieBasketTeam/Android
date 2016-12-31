package com.moviebasket.android.client.mypage.movie_pack_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;

import java.util.ArrayList;


/**
 * Created by pilju on 2016-12-27.
 */


public class PackAdapter extends RecyclerView.Adapter<PackViewHolder> {

    ArrayList<PackDatas> mDatas;
    View.OnClickListener clickListener;
    private ViewGroup parent;

    public PackAdapter() {

    }

    public PackAdapter(ArrayList<PackDatas> mDatas, View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.mDatas = mDatas;
    }


    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;

        // 뷰홀더 패턴을 생성하는 메소드.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_pack, parent, false);
        if(this.clickListener!=null)
            itemView.setOnClickListener(clickListener);
        PackViewHolder viewHolder = new PackViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PackViewHolder holder, int position) {
        //리싸이클뷰에 항목을 뿌려주는 메소드.

        //holder.movieImage.setImageResource(mDatas.get(position).movieImage);
        Glide.with(parent.getContext()).load(mDatas.get(position).movieImage).into(holder.getMovieImageView());

        holder.movieName.setText(mDatas.get(position).movieName);
        holder.basketName.setText(mDatas.get(position).basketName);
        holder.BasketUserName.setText(mDatas.get(position).BasketUserName);
        holder.year.setText(String .valueOf(mDatas.get(position).year));
        holder.director.setText(mDatas.get(position).director);
        holder.downCount.setText(String .valueOf(mDatas.get(position).downCount));

        if (mDatas.get(position).heartImg == 0) {
            holder.heartImg.setImageResource(R.drawable.sub_no_heart);
        } else {
            holder.heartImg.setImageResource(R.drawable.sub_heart);
        }
//        holder.heartImg.setImageResource(mDatas.get(position).heartImg);


        if (mDatas.get(position).downImg == 0) {
            holder.downImg.setImageResource(R.drawable.sub_movie_down);
        } else {
            holder.downImg.setImageResource(R.drawable.sub_movie_nodown);
        }
//        holder.downImg.setImageResource(mDatas.get(position).downImg);
    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}

