package com.androidtest.retroboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

public class DetailActivity extends Activity {
    TextView tvTitle, tvDesc, tvHits, tvWriter, tvDate;
    Button btnDelete, btnUpdate;
    static int hits=0;
    static int position = 0;
    final String URL = "http://172.16.61.106:3005";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIservice apiService = retrofit.create(APIservice.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvHits = (TextView) findViewById(R.id.tvHits);
        tvWriter = (TextView) findViewById(R.id.tvWriter);
        tvDate = (TextView) findViewById(R.id.tvDate);



        Intent intent = getIntent();

        position = intent.getIntExtra("position",0);
        hits = Integer.parseInt(intent.getStringExtra("getHits"));
        hits++;



        tvTitle.setText("제목 : "+intent.getStringExtra("getTitle"));
        tvDesc.setText("내용 : "+intent.getStringExtra("getDesc"));
        tvHits.setText("조회 수  "+hits+"회");
        tvWriter.setText("작성자 : "+intent.getStringExtra("getWriter"));
        tvDate.setText("작성일자 : " +intent.getStringExtra("getDate"));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WriteActivity.class);
                startActivityForResult(intent, 200);

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode == 200){

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.putExtra("getHits", hits);
        intent.putExtra("position", position);
        Call<ListviewItem> call = apiService.updateHits(position, hits);
        call.enqueue(new Callback<ListviewItem>() {
            @Override
            public void onResponse(Call<ListviewItem> call, Response<ListviewItem> response) {
                Log.d("result: ", response.message());
            }

            @Override
            public void onFailure(Call<ListviewItem> call, Throwable t) {
                Log.e("result: ", t.getMessage());
            }
        });
        setResult(RESULT_OK, intent);
        finish();

    }
}
