package com.moviebasket.android.client.mypage.basket_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;

import java.util.ArrayList;

/**
 * Created by pilju on 2016-12-28.
 */

public class BasketListAdapter extends RecyclerView.Adapter<BasketListViewHolder>{

    ArrayList<BasketListDatas> mDatas;
    View.OnClickListener clickListener;

    private ViewGroup parent;
    private View itemView;
    public BasketListAdapter(ArrayList<BasketListDatas> mDatas) {
        this.mDatas = mDatas;
    }

    public BasketListAdapter(ArrayList<BasketListDatas> mDatas, View.OnClickListener clickListener) {
        this.mDatas = mDatas;
        this.clickListener = clickListener;
    }

    @Override
    public BasketListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 뷰홀더 패턴을 생성하는 메소드.

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_bl, parent, false);
        if(this.clickListener!=null)
            itemView.setOnClickListener(clickListener);

        this.parent = parent;
        this.itemView = itemView;
        BasketListViewHolder viewHolder = new BasketListViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BasketListViewHolder holder, int position) {
        //리싸이클뷰에 항목을 뿌려주는 메소드.
        Glide.with(parent.getContext()).load(mDatas.get(position).basket_image).into(holder.basketImg);
        holder.basketName.setText(mDatas.get(position).basket_name);
        holder.downCount.setText(String.valueOf(mDatas.get(position).basket_like));

    }

    @Override
    public int getItemCount() {
        return (mDatas != null) ? mDatas.size() : 0;
    }
}
