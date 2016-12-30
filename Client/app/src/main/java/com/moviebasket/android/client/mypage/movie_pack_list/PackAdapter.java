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
        holder.movie_image.setImageResource(mDatas.get(position).image);
        holder.owner.setText(mDatas.get(position).owner);
        holder.likecount.setText(mDatas.get(position).likecount);
        holder.title.setText(mDatas.get(position).title);
        holder.direct_country.setText(mDatas.get(position).directer + "/" +mDatas.get(position).country);

    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}

