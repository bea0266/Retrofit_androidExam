package com.androidtest.navilogin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.PostItem;
import com.androidtest.navilogin.R;
import com.androidtest.navilogin.fragment.BoardFragment;
import com.kakao.sdk.user.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androidtest.navilogin.activity.MainActivity.getUserName;

public class WriteboxActivity extends AppCompatActivity {
    private ActionBar actionBar;
    EditText etTitle, etDesc;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String write_date;
    Calendar cal  = Calendar.getInstance();
    final static String URL = "http://192.168.35.4:3030";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writebox);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDesc = (EditText) findViewById(R.id.etDesc);

        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        actionBar.setTitle("게시글 작성");






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cancle:
                finish();
                break;
            case R.id.action_write:
                int def_num = 0;//생성할때 0으로 초기화
                write_date = sdf.format(cal.getTime());
                String title = etTitle.getText().toString();
                String description = etDesc.getText().toString();
                String writer = getUserName();
                int hits, comments, likes = def_num;

                //retrofit start
                Call<PostItem> call = apiService.addPost(writer, title, description, 0, 0,0, write_date);
                call.enqueue(new Callback<PostItem>() {
                    @Override
                    public void onResponse(Call<PostItem> call, Response<PostItem> response) {
                        Log.d("result", Boolean.toString(response.isSuccessful()));
                    }

                    @Override
                    public void onFailure(Call<PostItem> call, Throwable t) {
                        Log.e("error", t.getMessage());
                    }
                });

                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
