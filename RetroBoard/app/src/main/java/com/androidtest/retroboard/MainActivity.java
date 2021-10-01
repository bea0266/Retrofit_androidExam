package com.androidtest.retroboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Button btnWrite;
    CustomAdapter adapter;
    ListView postList;

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
                adapter.addItem(getTitle, 0, getWriter, "2021-10-01");
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "처리 도중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}




