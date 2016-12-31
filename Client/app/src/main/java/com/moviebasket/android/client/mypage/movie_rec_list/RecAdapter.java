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
        holder.movie_image.setImageResource(R.drawable.sub_movie_down);//임시로 해놓은 이미지! 글라인더 해야함 데이터 받아서!!!
        holder.owner.setText(mDatas.get(position).owner);
        holder.likecount.setText(mDatas.get(position).movie_like);
        holder.movie_pub_date.setText(mDatas.get(position).movie_pub_date);
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


    }

    @Override
    public int getItemCount() {

        return (mDatas != null) ? mDatas.size() : 0;
    }
}
