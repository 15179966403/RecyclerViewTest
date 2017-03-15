package com.hrc.administrator.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/15.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClickListener(View view, int position);
        void onItemLongClickListener(View view,int position);
    }

    public HomeAdapter(Context context,List<String> data){
        mInflater=LayoutInflater.from(context);
        mData=data;
    }

    /**
     * 添加下标为position的子项数据
     * @param position 子项的下标
     */
    public void addData(int position){
        mData.add(position,"Insert one");
        notifyItemInserted(position);
    }

    /**
     * 移除下标为position的子项数据
     * @param position 子项的下标
     */
    public void removeData(int position){
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder=new MyViewHolder(mInflater.inflate(R.layout.item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        int height=(new Random().nextInt(150))+50;
        holder.tv.setText(mData.get(position));
        holder.tv.setHeight(height);
        if (mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mListener.onItemClickListener(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mListener.onItemLongClickListener(holder.itemView,pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
