package com.androidtest.navilogin.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.androidtest.navilogin.CommentItem;
import com.androidtest.navilogin.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private CommentAdapter.OnItemClickListener mListener = null;
    private ArrayList<CommentItem> commData = null;
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }
    public void setOnItemClickListener(CommentAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  tvCommWriter, tvCommWriteDate, tvContent;
        ViewHolder(View itemView) {
            super(itemView);


            tvContent = (TextView) itemView.findViewById(R.id.tvContents);
            tvCommWriter = (TextView) itemView.findViewById(R.id.tvCommWriter);
            tvCommWriteDate = (TextView) itemView.findViewById(R.id.tvCommWriteDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(v,pos);

                    }
                }
            });
        }

    }

    CommentAdapter(ArrayList<CommentItem> list) {
        commData = list ;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comment,parent,false);
        CommentAdapter.ViewHolder holder = new CommentAdapter.ViewHolder(view);

        return holder ;
    }



    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        CommentItem commentItems = commData.get(position);
        holder.tvCommWriter.setText(commentItems.getCommWriter());
        holder.tvCommWriteDate.setText(commentItems.getCommWriteDate());
        holder.tvContent.setText(commentItems.getContents());

    }

    @Override
    public int getItemCount() {
        return commData.size() ;
    }
    public CommentItem getItem(int position){
        return commData.get(position);
    }
    public void removeItem(int position){
        commData.remove(position);
    }

    public void listCleaner(){

       commData.clear();

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(int commNo ,int postNo,  String commWriter, String commWriteDate, String contents){
        CommentItem item = new CommentItem();

        item.setPostNo(postNo);
        item.setCommNo(commNo);
        item.setCommWriteDate(commWriteDate);
        item.setCommWriter(commWriter);
        item.setContents(contents);
        commData.add(item);


    }

}
