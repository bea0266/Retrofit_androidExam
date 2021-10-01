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
    Button btnWrite; // 작성 버튼(메인)
    CustomAdapter adapter; // 리스트뷰를 연결하기 위한 커스텀어댑터
    ListView postList; // 리스트뷰
    private long backKeyPressedTime = 0; //뒤로 가기를 누른 시간
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        postList = (ListView) findViewById(R.id.listView);

        adapter = new CustomAdapter(); //커스텀어댑터 객체 생성
        postList.setAdapter(adapter);//어댑터 세팅


        /*
            작성 버튼 눌렀을때
         */
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WriteActivity.class); //글쓰기 액티비티로 이동
                startActivityForResult(intent, 100); // 인텐트 이동
            }
        });

    }
    /*
        글 작성 후 메인액티비티에 리스트 추가하기
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                String getTitle = data.getStringExtra("Title");// 글 제목
                String getWriter = data.getStringExtra("Writer");// 작성자
                adapter.addItem(getTitle, 0, getWriter, "2021-10-01"); // 리스트 추가
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "처리 도중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        /*
            뒤로 가기 두번 누르면 종료되는 메소드
         */
        //super.onBackPressed();
        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.",
                    Toast.LENGTH_SHORT).show();
            return;

        }


        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }

}


<<<<<<< HEAD
    public void addItem(String title,  String description,  int hits, String writer, String write_date){
        ListviewItem item = new ListviewItem();

        item.setWrite_date(write_date);
        item.setWriter(writer);
        item.setHits(hits);
        item.setTitle(title);
        item.setDescription(description);
        listviewItemList.add(item);
    }
}
=======


>>>>>>> 45667661f05695007ed97fdda8464da4b2f6012e
