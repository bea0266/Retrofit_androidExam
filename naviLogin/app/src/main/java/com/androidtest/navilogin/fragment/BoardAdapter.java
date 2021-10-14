package com.androidtest.navilogin.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.navilogin.PostItem;
import com.androidtest.navilogin.R;

import java.util.ArrayList;
import java.util.Collections;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ArrayList<PostItem> postData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvHits, tvWriter, tvDate, tvLikes, tvComments;
        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvHits = (TextView) itemView.findViewById(R.id.tvHits);
            tvWriter = (TextView) itemView.findViewById(R.id.tvWriter);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
            tvComments = (TextView) itemView.findViewById(R.id.tvComments);
        }

    }

    BoardAdapter(ArrayList<PostItem> list) {
        postData = list ;
    }

    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
       ViewHolder holder = new ViewHolder(view);

        return holder ;
    }


    @Override
    public void onBindViewHolder(BoardAdapter.ViewHolder holder, int position) {
        PostItem postItems = postData.get(position);
        holder.tvTitle.setText(postItems.getTitle());
        holder.tvHits.setText("조회수 "+postItems.getHits());
        holder.tvWriter.setText(postItems.getWriter());
        holder.tvDate.setText(postItems.getWrite_date());
        holder.tvLikes.setText("좋아요 "+postItems.getLikes());
        holder.tvComments.setText("댓글 "+postItems.getComments());
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
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(int postNo, String title, String description, String writer, String write_date,
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

        postData.add(item);


    }
}
