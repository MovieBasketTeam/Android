package com.moviebasket.android.client.mypage.movie_pack_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviebasket.android.client.R;

import java.util.ArrayList;


/**
 * Created by pilju on 2016-12-27.
 */


public class PackAdapter extends RecyclerView.Adapter<PackViewHolder> {

    ArrayList<PackDatas> mDatas;
    View.OnClickListener clickListener;

    public PackAdapter() {

    }

    public PackAdapter(ArrayList<PackDatas> mDatas, View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.mDatas = mDatas;
    }


    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.movieImage.setImageResource(mDatas.get(position).movieImage);
        holder.basketName.setText(mDatas.get(position).basketName);
        holder.BasketUserName.setText(mDatas.get(position).BasketUserName);
        holder.year.setText(mDatas.get(position).year);
        holder.director.setText(mDatas.get(position).director);
        holder.downCount.setText(mDatas.get(position).downCount);
        holder.heartImg.setImageResource(mDatas.get(position).heartImg);
        holder.downImg.setImageResource(mDatas.get(position).downImg);
    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}

