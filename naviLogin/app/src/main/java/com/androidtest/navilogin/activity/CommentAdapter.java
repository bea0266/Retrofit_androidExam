package com.androidtest.navilogin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import retrofit2.Call;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.CommentItem;
import com.androidtest.navilogin.R;
import com.androidtest.navilogin.fragment.BoardAdapter;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.test.InstrumentationRegistry.getContext;
import static com.androidtest.navilogin.activity.MainActivity.URL;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>  {



    private BoardAdapter.OnItemClickListener mListener = null;
    private ArrayList<CommentItem> commData = null;
    private Context context;
    private DetailActivity activity;
    private int pos;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btnMore;
        TextView  tvCommWriter, tvCommWriteDate, tvContent;
        ViewHolder(View itemView) {
            super(itemView);


            tvContent = (TextView) itemView.findViewById(R.id.tvContents);
            tvCommWriter = (TextView) itemView.findViewById(R.id.tvCommWriter);
            tvCommWriteDate = (TextView) itemView.findViewById(R.id.tvCommWriteDate);
            btnMore = (ImageButton) itemView.findViewById(R.id.btnMore);

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context,v);
                    pos = getAdapterPosition();
                    popupMenu.getMenuInflater().inflate(R.menu.comment_action, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId()==R.id.commDelete)
                            {
                                int postNo = commData.get(pos).getPostNo();
                                int commNo = commData.get(pos).getCommNo();

                                Call<List<CommentItem>> call = apiService.deleteComment(postNo, commNo);
                                call.enqueue(new Callback<List<CommentItem>>() {
                                    @Override
                                    public void onResponse(Call<List<CommentItem>> call, Response<List<CommentItem>> response) {
                                        Log.d("success", "delete 성공");
                                        Toast.makeText(context, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<List<CommentItem>> call, Throwable t) {
                                        Log.e("delete_fail", t.getMessage());
                                        Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                Intent intent = ((Activity)context).getIntent();
                                ((Activity)context).finish(); //현재 액티비티 종료 실시
                                ((Activity)context).overridePendingTransition(0, 0); //효과 없애기
                                ((Activity)context).startActivity(intent); //현재 액티비티 재실행 실시
                                ((Activity)context).overridePendingTransition(0, 0); //효과 없애기

                                return true;
                            } else if (item.getItemId()==R.id.commModify){

                                activity.isClicked = true;
                                activity.changeLayout(pos);


                            }

                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    CommentAdapter(ArrayList<CommentItem> list, Context context, DetailActivity activity) {
        commData = list ;
        this.context = context;
        this.activity = activity;
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

    public void getItemNo (int position){
        int commNo = commData.get(position).getCommNo();
        int postNo = commData.get(position).getPostNo();

    }

    @Override
    public int getItemCount() {
        return commData.size() ;
    }
    public CommentItem getItem(int position){
        return commData.get(position);
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
