package com.androidtest.retroboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position", Integer.toString(position));
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("getTitle", adapter.getItem(position).getTitle());
                intent.putExtra("getWriter", adapter.getItem(position).getWriter());
                intent.putExtra("getHits", Integer.toString(adapter.getItem(position).getHits()));
                intent.putExtra("getDate", adapter.getItem(position).getWrite_date());
                intent.putExtra("getDesc", adapter.getItem(position).getDescription());
                intent.putExtra("position", position);
                startActivityForResult(intent,101);

            }
        });

        postList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("status", "now long touch" );
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("게시글 삭제")
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<ListviewItem> call = apiService.deletePost(position+1);
                                call.enqueue(new Callback<ListviewItem>() {
                                    @Override
                                    public void onResponse(Call<ListviewItem> call, Response<ListviewItem> response) {
                                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        adapter.removeItem(position);
                                        adapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onFailure(Call<ListviewItem> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "요청 실패 : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();
                return true;
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
                        Log.e("error", t.getMessage());
                        Toast.makeText(getApplicationContext(), "처리 도중 오류가 발생했습니다: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else if(requestCode==101){

                int getHits = data.getIntExtra("getHits",0);
                adapter.getItem(data.getIntExtra("position",0)).setHits(getHits);
                adapter.getItem(data.getIntExtra("position",0)).setWriter(data.getStringExtra("getWriter"));
                adapter.getItem(data.getIntExtra("position",0)).setTitle(data.getStringExtra("getTitle"));
                adapter.getItem(data.getIntExtra("position",0)).setDescription(data.getStringExtra("getDesc"));
                adapter.getItem(data.getIntExtra("position",0)).setWrite_date(data.getStringExtra("getDate"));

                adapter.notifyDataSetChanged();


            } else {
                        Toast.makeText(getApplicationContext(), "처리 도중 오류가 발생했습니다.(요청 오류).", Toast.LENGTH_SHORT).show();
            }
        }
    } // 여기까지가 온액티비티리절트

    void showDialog() {


    }
}




