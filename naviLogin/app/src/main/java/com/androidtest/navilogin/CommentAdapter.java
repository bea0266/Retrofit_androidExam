package com.androidtest.navilogin;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.Collections;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private CommentAdapter.OnItemClickListener mListener = null;
    private ArrayList<PostItem> commData = null;
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

    CommentAdapter(ArrayList<PostItem> list) {
        commData = list ;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        CommentAdapter.ViewHolder holder = new CommentAdapter.ViewHolder(view);

        return holder ;
    }



    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        PostItem postItems = postData.get(position);
        holder.tvTitle.setText(postItems.getTitle());
        holder.tvHits.setText("조회수 "+postItems.getHits());
        holder.tvWriter.setText(postItems.getWriter());

    }

    @Override
    public int getItemCount() {
        return postData.size() ;
    }
    public PostItem getItem(int position){
        return postData.get(position);
    }
    public void removeItem(int position){
        postData.remove(position);
    }
    public void swapItem(int cnt){

//커밋 이후 작성할 코드
        for(int i=cnt-1; i>0; i-- )
            Collections.swap(postData, i, i-1 );
        for(int i=0; i<postData.size(); i++) {
            Log.d("listorder", "listorder[" + i + "]" + postData.get(i).getTitle());
        }

    }
    public void listCleaner(){

        postData.clear();

    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(int postNo, long userId, String title, String description, String writer, String write_date,
                        int hits, int comments, int likes){
        PostItem item = new PostItem();

        item.setPostNo(postNo);
        item.setWrite_date(write_date);
        item.setDescription(description);
        item.setWriter(writer);
        item.setHits(hits);
        item.setTitle(title);
        item.setComments(comments);
        item.setLikes(likes);
        item.setUserId(userId);

        postData.add(item);


    }

}
