package com.androidtest.navilogin.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.CommentItem;
import com.androidtest.navilogin.PostItem;
import com.androidtest.navilogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androidtest.navilogin.activity.MainActivity.URL;

public class DetailActivity extends AppCompatActivity {
    ActionBar actionBar;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal  = Calendar.getInstance();
    TextView tvTitle, tvWriter, tvDesc, tvHits, tvLikes, tvComments,tvDate;
    EditText etComment; //댓글작성창(나중에 구현)
    RecyclerView recycleComment; // 나중에 구현
    Button btnRegist, btnLike; // 나중에 구현
    CommentAdapter commentAdapter;
    int postNo;
    String commWriter;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected void onResume() {
        super.onResume();
        commentAdapter.notifyDataSetChanged();
        Call<ResponseBody> call = apiService.getComment(postNo); //댓글을 불러온다.
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseData = response.body().string();
                    Log.d("responseData", responseData);
                    if(responseData.length()==0){
                       commentAdapter.listCleaner();
                       commentAdapter.notifyDataSetChanged();
                    } else {

                        commentAdapter.listCleaner();
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int postNo = jsonObject.getInt("postNo");
                            int commNo = jsonObject.getInt("commNo");
                            String commWriter = jsonObject.getString("commWriter");
                            String commWriteDate = jsonObject.getString("commWriteDate");
                            String contents = jsonObject.getString("contents");

                            commentAdapter.addItem(commNo, postNo, commWriter, commWriteDate, contents);
                            commentAdapter.notifyDataSetChanged();
                            Log.d("itemadd", "add comment item : commNo:"+commNo+", postNo:"+postNo+", commWriter:"+commWriter
                            +" commWriteDate:"+commWriteDate+", contents:"+contents);
                        }


                    }
                } catch (IOException | JSONException e) {

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvWriter = (TextView) findViewById(R.id.tvWriter);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvHits = (TextView) findViewById(R.id.tvHits);
        tvLikes = (TextView) findViewById(R.id.tvLikes);
        tvComments = (TextView) findViewById(R.id.tvComments);
        tvDate = (TextView) findViewById(R.id.tvDate);
        btnLike = (Button) findViewById(R.id.btnLike);
        btnRegist = (Button) findViewById(R.id.btnRegist);
        etComment = (EditText) findViewById(R.id.etComment);
        recycleComment = (RecyclerView) findViewById(R.id.recycleComment);

        recycleComment.setLayoutManager(new LinearLayoutManager(getApplicationContext())); // 레이아웃매니저 설정
        ArrayList<CommentItem> list = new ArrayList<>(); //댓글클래스를 제네릭스로 가지는 리스트 구현
        commentAdapter = new CommentAdapter(list); // 어댑터 생성 및 리스트 부착
        recycleComment.setAdapter(commentAdapter); // 어댑터 사용 가능



        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();


        Intent intent = getIntent();
        postNo = intent.getIntExtra("postNo",1 );
        commWriter = intent.getStringExtra("userName");


        btnRegist.setOnClickListener(new View.OnClickListener() { // 댓글을 등록할때
            @Override
            public void onClick(View v) {
                String commWriteDate = sdf.format(cal.getTime());
                String comment = etComment.getText().toString();
                Call<CommentItem> call = apiService.addComment(postNo, commWriter, comment, commWriteDate);
                call.enqueue(new Callback<CommentItem>() {
                    @Override
                    public void onResponse(Call<CommentItem> call, Response<CommentItem> response) {
                        Log.d("addComment", "postNo: "+postNo+" comment++");
                        finish();//인텐트 종료
                        // overridePendingTransition(0, 0);//인텐트 효과 없애기
                        Intent intent = getIntent(); //인텐트
                        startActivity(intent); //액티비티 열기
                        //  overridePendingTransition(0, 0);//인텐트 효과 없애기

                    }

                    @Override
                    public void onFailure(Call<CommentItem> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });

            }
        });


        btnLike.setOnClickListener(new View.OnClickListener() { //좋아요 누를때
            @Override
            public void onClick(View v) {
                Call<PostItem> call = apiService.addLike(postNo);
                call.enqueue(new Callback<PostItem>() {
                    @Override
                    public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                        tvLikes.setText(Integer.toString(Integer.parseInt(tvLikes.getText().toString())+1));

                        Log.d("addlike", "like++");
                    }

                    @Override
                    public void onFailure(Call<PostItem> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });
            }
        });

        Call<ResponseBody> call = apiService.getPostinfo(postNo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resJson = response.body().string();
                    Log.d("responseData", resJson);

                    if(resJson.length()>0){
                        JSONArray jsonArray = new JSONArray(resJson);


                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        String title = jsonObject.getString("title");
                        String writer = jsonObject.getString("writer");
                        String description = jsonObject.getString("description");
                        int hits = jsonObject.getInt("hits");
                        int likes = jsonObject.getInt("likes");
                        int comments = jsonObject.getInt("comments");
                        String write_date = jsonObject.getString("write_date");

                        tvTitle.setText(title);
                        tvDate.setText(write_date);
                        tvDesc.setText(description);
                        tvWriter.setText(writer);
                        tvHits.setText(Integer.toString(hits));
                        tvLikes.setText(Integer.toString(likes));
                        tvComments.setText(Integer.toString(comments));

                    }



                } catch (IOException | JSONException e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.update:
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("title", tvTitle.getText().toString());
                intent.putExtra("description", tvDesc.getText().toString());
                intent.putExtra("writer", tvWriter.getText().toString());
                intent.putExtra("postNo", postNo);
                startActivityForResult(intent, 0);
                break;
            case R.id.delete:
                Call<PostItem> call = apiService.deletePost(postNo);
                call.enqueue(new Callback<PostItem>() {
                    @Override
                    public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                        Toast.makeText(getApplicationContext(), "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Log.d("deletePost", "게시글이 삭제되었습니다.");
                        finish();
                    }

                    @Override
                    public void onFailure(Call<PostItem> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String write_date = data.getStringExtra("write_date");

            tvTitle.setText(title);
            tvDesc.setText(description);
            tvDate.setText(write_date);


        }
    }
}
