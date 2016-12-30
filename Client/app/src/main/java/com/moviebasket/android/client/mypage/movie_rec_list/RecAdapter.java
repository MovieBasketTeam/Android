package com.moviebasket.android.client.mypage.movie_rec_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moviebasket.android.client.R;
import java.util.ArrayList;

/**
 * Created by pilju on 2016-12-28.
 */

public class RecAdapter extends RecyclerView.Adapter<RecViewHolder> {

    ArrayList<RecDatas> mDatas;
    View.OnClickListener clickListener;
    private View itemView;

    public RecAdapter(ArrayList<RecDatas> mDatas) {
        this.mDatas = mDatas;
    }
    public RecAdapter(ArrayList<RecDatas> mDatas, View.OnClickListener clickListener) {
        this.mDatas = mDatas;
        this.clickListener = clickListener;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // 뷰홀더 패턴을 생성하는 메소드.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_rec, parent, false);

        if( this.clickListener != null )
            itemView.setOnClickListener(clickListener);

        RecViewHolder viewHolder = new RecViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {

        //리싸이클뷰에 항목을 뿌려주는 메소드.
        holder.movie_image.setImageResource(mDatas.get(position).image);
        holder.owner.setText(mDatas.get(position).owner);
        holder.likecount.setText(mDatas.get(position).likecount);
        holder.title.setText(mDatas.get(position).title);
        holder.direct_country.setText(mDatas.get(position).directer + "/" +mDatas.get(position).country);
        holder.book_mark.setImageResource(mDatas.get(position).book_mark);
        holder.heart.setImageResource(mDatas.get(position).heart);

    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}
