package com.androidtest.navilogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.R;
import com.androidtest.navilogin.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androidtest.navilogin.activity.MainActivity.URL;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle, tvWriter, tvDesc, tvHits, tvLikes, tvComments,tvDate;
    EditText etComment; //댓글작성창(나중에 구현)
    RecyclerView recycleComment; // 나중에 구현
    Button btnRegist; // 나중에 구현

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

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

        Intent intent = getIntent();
        int postNo = intent.getIntExtra("postNo",1 );
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
        getMenuInflater().inflate(R.menu.detail_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.update:

                break;
            case R.id.delete:
                

        }


        return super.onOptionsItemSelected(item);
    }
}
