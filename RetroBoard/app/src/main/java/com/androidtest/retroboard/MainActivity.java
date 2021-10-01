package com.androidtest.retroboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    final static String URL = "http://192.168.35.4:3005";
    Button btnWrite;
    CustomAdapter adapter;
    ListView postList;
    /*
        retrofit 실행
     */
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIservice apiService = retrofit.create(APIservice.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btnWrite = (Button) findViewById(R.id.btnWrite);
        postList = (ListView) findViewById(R.id.listView);

        adapter = new CustomAdapter();
        postList.setAdapter(adapter);

        //adapter.addItem("제목", 1,"baegood", "2021-10-01");
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                String getTitle = data.getStringExtra("Title");
                String getWriter = data.getStringExtra("Writer");
                String getDesc = data.getStringExtra("Description");
                String getDate = data.getStringExtra("Write_date");

                Call<ListviewItem> call = apiService.sendMypost(getTitle, getWriter, getDesc, getDate, 0);
                call.enqueue(new Callback<ListviewItem>() {
                    @Override
                    public void onResponse(Call<ListviewItem> call, Response<ListviewItem> response) {
                        adapter.addItem(getTitle, getDesc, 0, getWriter, getDate);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ListviewItem> call, Throwable t) {
                        Log.e("dataset", "title:"+getTitle+" description:"+getDesc+" writer:"+getWriter+" write_date:"+getDate);
                        Toast.makeText(getApplicationContext(), "처리 도중 오류가 발생했습니다: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                        Toast.makeText(getApplicationContext(), "처리 도중 오류가 발생했습니다.(요청 오류).", Toast.LENGTH_SHORT).show();
            }
        }
    }

}




