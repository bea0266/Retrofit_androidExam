package com.androidtest.retroboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteUpdateActivity extends Activity {
    Button btnUpdate, btnClear;
    EditText etTitle, etDesc, etWriter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String write_date;
    Calendar cal  = Calendar.getInstance();
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
        etWriter.setText(getWriter);
        etTitle.setText(getTitle);
        etDesc.setText(getDesc);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_date = sdf.format(cal.getTime());
                Intent intent = new Intent(WriteUpdateActivity.this, DetailActivity.class);
                intent.putExtra("Title", etTitle.getText().toString());
                intent.putExtra("Writer", etWriter.getText().toString());
                intent.putExtra("Description", etDesc.getText().toString());
                intent.putExtra("Write_date", write_date);// 추후에 사용
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
