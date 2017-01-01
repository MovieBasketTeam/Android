package com.moviebasket.android.client.mypage.movie_rec_list;

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

public class RecAdapter extends RecyclerView.Adapter<RecViewHolder> {

    ArrayList<RecDatas> mDatas;
    View.OnClickListener recyclerclickListener;
    View.OnClickListener clickListener;
    private View itemView;
    private ViewGroup parent;


    public RecAdapter(ArrayList<RecDatas> mDatas) {
        this.mDatas = mDatas;
    }
    public RecAdapter(ArrayList<RecDatas> mDatas, View.OnClickListener clickListener) {
        this.mDatas = mDatas;
        this.recyclerclickListener = clickListener;
    }
    public RecAdapter(ArrayList<RecDatas> mDatas, View.OnClickListener recyclerclickListener, View.OnClickListener clickListener) {
        this.mDatas = mDatas;
        this.recyclerclickListener = recyclerclickListener;
        this.clickListener = clickListener;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        // 뷰홀더 패턴을 생성하는 메소드.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_rec, parent, false);

        if( this.recyclerclickListener != null )
            itemView.setOnClickListener(recyclerclickListener);

        RecViewHolder viewHolder = new RecViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {

        //리싸이클뷰에 항목을 뿌려주는 메소드.
        //holder.movie_image.setImageResource(R.drawable.sub_movie_down);
        Glide.with(parent.getContext()).load(mDatas.get(position).movie_image).into(holder.getMovieImageView());
        holder.owner.setText(mDatas.get(position).owner);
        holder.likecount.setText(String.valueOf(mDatas.get(position).movie_like));
        holder.movie_pub_date.setText(String.valueOf(mDatas.get(position).movie_pub_date));
        holder.title.setText(mDatas.get(position).movie_title);
        holder.direct.setText(mDatas.get(position).movie_director);

        if(mDatas.get(position).book_mark==1) {
            holder.book_mark.setImageResource(R.drawable.sub_movie_down);
        }else{
            holder.book_mark.setImageResource(R.drawable.sub_movie_nodown);
        }

        if(mDatas.get(position).is_liked==1){
            holder.is_liked.setImageResource(R.drawable.sub_heart);
        }else{
            holder.is_liked.setImageResource(R.drawable.sub_no_heart);
        }

        if(clickListener != null){
            holder.book_mark.setOnClickListener(clickListener);
            holder.is_liked.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}
