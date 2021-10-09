package com.androidtest.retroboard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    final static String URL = "http://localhost:3005";
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

        Call<ResponseBody> call = apiService.selectPost();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseData = response.body().string();
                    Log.d("responsebody", responseData);
                    if(responseData.equals("[]")) {}
                    else {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title");
                            String writer = jsonObject.getString("writer");
                            String description = jsonObject.getString("description");
                            int hits = jsonObject.getInt("hits");
                            String date = jsonObject.getString("write_date");


                            adapter.addItem(title, description, hits, writer,date);
                            adapter.notifyDataSetChanged();
                            Log.d("itemadd", i + 1 + "번째 아이템이 추가되었습니다." + title + "," + description + "," + hits + "," +
                                    writer + "," + date);
                        }

                    }
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("responsebody-error", t.getMessage());
            }
        });

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
                                Call<List<ListviewItem>> call = apiService.deletePost(position+1, adapter.getCount());
                                call.enqueue(new Callback<List<ListviewItem>>() {
                                    @Override
                                    public void onResponse(Call<List<ListviewItem>> call, Response<List<ListviewItem>> response) {
                                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        adapter.removeItem(position);
                                        Log.d("listviewsize", Integer.toString(adapter.getCount()));
                                        adapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onFailure(Call<List<ListviewItem>> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "요청 실패 : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("response-error", t.getMessage());
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
                       /*
                        //커밋 후 추가 예정
                        if(adapter.getCount()>1){

                            adapter.swapItem(adapter.getCount());
                        }*/
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                        Log.d("listviewsize", Integer.toString(adapter.getCount()));
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


}








