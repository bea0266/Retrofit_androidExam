package com.androidtest.retroboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public class WriteUpdateActivity extends Activity {
    Button btnUpdate, btnClear;
    EditText etTitle, etDesc, etWriter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String write_date;
    Calendar cal  = Calendar.getInstance();
    final static String URL = "http://192.168.35.4:3005";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIservice apiService = retrofit.create(APIservice.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_update);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etWriter = (EditText) findViewById(R.id.etWriter);
        etDesc = (EditText) findViewById(R.id.etDesc);

        Intent intent = getIntent();
        String getTitle = intent.getStringExtra("title");
        String getDesc = intent.getStringExtra("description");
        String getWriter = intent.getStringExtra("writer");
        int getPosition = intent.getIntExtra("position", 0);
        int position = getPosition+1;

        etWriter.setText(getWriter);
        etTitle.setText(getTitle);
        etDesc.setText(getDesc);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTitle = etTitle.getText().toString();
                String getWriter = etWriter.getText().toString();
                String getDesc = etDesc.getText().toString();

                write_date = sdf.format(cal.getTime());
                Intent intent = new Intent(WriteUpdateActivity.this, DetailActivity.class);
                intent.putExtra("Title", etTitle.getText().toString());
                intent.putExtra("Writer", etWriter.getText().toString());
                intent.putExtra("Description", etDesc.getText().toString());
                intent.putExtra("Write_date", write_date);// 추후에 사용

                Call<ListviewItem> call = apiService.updatePost(position, getTitle, getWriter, getDesc, write_date);

                call.enqueue(new Callback<ListviewItem>() {
                    @Override
                    public void onResponse(Call<ListviewItem> call, Response<ListviewItem> response) {
                        Toast.makeText(getApplicationContext(),"게시글이 수정되었습니다.", Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onFailure(Call<ListviewItem> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"실패하였습니다: "+t.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                });

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
