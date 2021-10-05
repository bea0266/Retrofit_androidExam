package com.androidtest.retroboard;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import java.util.List;
import java.util.ListIterator;

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
    final String URL = "http://localhost:3005";

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
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        Intent intent = getIntent();

        position = intent.getIntExtra("position",0);
        hits = Integer.parseInt(intent.getStringExtra("getHits"));
        hits++;

        String getTitle = intent.getStringExtra("getTitle");
        String getDesc = intent.getStringExtra("getDesc");
        String getWriter = intent.getStringExtra("getWriter");
        String getDate = intent.getStringExtra("getDate");




        tvTitle.setText(getTitle);
        tvDesc.setText(getDesc);
        tvHits.setText(Integer.toString(hits));
        tvWriter.setText(getWriter);
        tvDate.setText(getDate);

        /*
            수정 버튼 누를 시 writeactivity로 이동하고 수정버튼 활성화
         */
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WriteUpdateActivity.class);
                intent.putExtra("title", getTitle);
                intent.putExtra("description",  getDesc);
                intent.putExtra("writer",  getWriter);
                intent.putExtra("position", position);

                startActivityForResult(intent, 200);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call<ListviewItem> call = apiService.deletePost(position+1);
                call.enqueue(new Callback<ListviewItem>() {
                    @Override
                    public void onResponse(Call<ListviewItem> call, Response<ListviewItem> response) {
                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<ListviewItem> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "요청 실패 : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode == 200){ //수정 완료되었을경우
                String getTitle = data.getStringExtra("Title");
                String getDesc = data.getStringExtra("Description");
                String getDate = data.getStringExtra("Write_date");
                String getWriter = data.getStringExtra("Writer");

                tvTitle.setText(getTitle);
                tvDesc.setText(getDesc);
                tvWriter.setText(getWriter);
                tvDate.setText(getDate);


            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.putExtra("getHits", hits);
        intent.putExtra("position", position);
        intent.putExtra("getTitle", tvTitle.getText().toString());
        intent.putExtra("getWriter", tvWriter.getText().toString());
        intent.putExtra("getDate", tvDate.getText().toString());
        intent.putExtra("getDesc", tvDesc.getText().toString());
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
