package com.moviebasket.android.client.tag.tagged;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.moviebasket.android.client.R;
import com.moviebasket.android.client.clickable.OneClickable;

import java.util.ArrayList;

/**
 * Created by Jiwon on 2017-01-03.
 */
public class TaggedAdapter extends RecyclerView.Adapter<TaggedBasketsViewHolder>{ArrayList<Baskets> mDatas;
    View.OnClickListener clickListener;
    View.OnClickListener subClickListener;
    OneClickable one;

    private ViewGroup parent;
    private View itemView;
    public TaggedAdapter(ArrayList<Baskets>mDatas){
        this.mDatas=mDatas;
    }

    public TaggedAdapter(ArrayList<Baskets>mDatas,View.OnClickListener clickListener,OneClickable one){
        this.mDatas=mDatas;
        this.clickListener=clickListener;
        this.one=one;
    }

    @Override
    public TaggedBasketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // 뷰홀더 패턴을 생성하는 메소드.

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_bl,parent,false);
        if(this.clickListener!=null)
            itemView.setOnClickListener(clickListener);

        this.parent=parent;
        this.itemView=itemView;
        TaggedBasketsViewHolder viewHolder=new TaggedBasketsViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaggedBasketsViewHolder holder,final int position){
        //리싸이클뷰에 항목을 뿌려주는 메소드.
        Glide.with(parent.getContext()).load(mDatas.get(position).basket_image).into(holder.basketImg);
        holder.basketName.setText(mDatas.get(position).basket_name);
        holder.downCount.setText(String.valueOf(mDatas.get(position).basket_like));

        if(mDatas.get(position).is_liked==0){
            holder.downBtn.setImageResource(R.drawable.sub_basket_down);
        }else{
            holder.downBtn.setImageResource(R.drawable.sub_basket_nodown);
        }
        holder.downBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                one.processOneMethodAtPosition(position);
            }
        });
    }



    @Override
    public int getItemCount(){
        return(mDatas!=null)?mDatas.size():0;
    }
}