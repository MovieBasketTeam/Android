package com.moviebasket.android.client.mypage.basket_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviebasket.android.client.R;

import java.util.ArrayList;

/**
 * Created by pilju on 2016-12-28.
 */

public class BasketListAdapter extends RecyclerView.Adapter<BasketListViewHolder>{

    ArrayList<BasketListDatas> mDatas;
    View.OnClickListener clickListener;
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
        BasketListViewHolder viewHolder = new BasketListViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BasketListViewHolder holder, int position) {
        //리싸이클뷰에 항목을 뿌려주는 메소드.
        holder.basketImg.setImageResource(mDatas.get(position).basketImg);
        holder.textImg.setImageResource(mDatas.get(position).textImg);
        holder.basketName.setText(mDatas.get(position).basketName);
        holder.downBtn.setImageResource(mDatas.get(position).downBtn);
        holder.downCount.setText(mDatas.get(position).downCount);
    }

    @Override
    public int getItemCount() {
        return (mDatas != null) ? mDatas.size() : 0;
    }
}
