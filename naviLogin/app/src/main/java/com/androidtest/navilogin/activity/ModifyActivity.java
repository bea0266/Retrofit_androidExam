package com.androidtest.navilogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.PostItem;
import com.androidtest.navilogin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static com.androidtest.navilogin.activity.MainActivity.createRetrofit;
import static com.androidtest.navilogin.activity.MainActivity.getUserId;
import static com.androidtest.navilogin.activity.MainActivity.getUserName;

/*
    수정 화면 액티비티
 */
public class ModifyActivity extends AppCompatActivity {
    EditText etTitle, etDesc;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String write_date;
    Calendar cal  = Calendar.getInstance();
    String title, description, chTitle, chDesc;;
    int postNo;
    Toolbar toolbar;

    ApiService apiService = createRetrofit();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifybox);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDesc = (EditText) findViewById(R.id.etDesc);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        postNo = intent.getIntExtra("postNo", 1);

        etTitle.setText(title);
        etDesc.setText(description);




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
                //retrofit start
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                title =  etTitle.getText().toString();
                description = etDesc.getText().toString();

                if(title.indexOf("\'")!=-1){

                    chTitle = title.replaceAll("'", "\'\'");
                } else chTitle = title;

                if(description.indexOf("\'")!=-1){

                    chDesc = description.replaceAll("\'","\'\'");
                } else chDesc = description;


                intent.putExtra("title", title);
                intent.putExtra("description", description);
                write_date = sdf.format(cal.getTime());
                intent.putExtra("write_date", write_date);
                Call<PostItem> call = apiService.updatePost(postNo, chTitle, chDesc, write_date);
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
                setResult(RESULT_OK,intent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }


}
