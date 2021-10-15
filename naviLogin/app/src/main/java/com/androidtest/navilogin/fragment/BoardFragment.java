package com.androidtest.navilogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.PostItem;
import com.androidtest.navilogin.R;
import com.androidtest.navilogin.activity.DetailActivity;
import com.androidtest.navilogin.activity.WriteboxActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    BoardAdapter boardAdapter;
    final static String URL = "http://172.16.61.106:3030";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    public BoardFragment() {

    }


    public static BoardFragment newInstance() {
        BoardFragment fragment = new BoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewgroup =(ViewGroup)inflater.inflate(R.layout.fragment_board, container, false);

        Button writeBtn = (Button)viewgroup.findViewById(R.id.btnWrite);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = (RecyclerView)viewgroup.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ArrayList<PostItem> list = new ArrayList<>();
        boardAdapter = new BoardAdapter(list);
        recyclerView.setAdapter(boardAdapter);

       boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(View v, int position) {
              Intent intent = new Intent(getActivity(), DetailActivity.class);
              startActivity(intent);
           }
       });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteboxActivity.class);
                startActivity(intent);
            }
        });

        return viewgroup;
    }

    @Override
    public void onResume() {
        super.onResume();

        Call<ResponseBody> call = apiService.getPosts();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseData = response.body().string();
                    Log.d("responseData", responseData);

                    if (responseData.equals("[]")) {

                    } else {
                        boardAdapter.listCleaner();
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int postNo = jsonObject.getInt("postNo");
                            String title = jsonObject.getString("title");
                            String writer = jsonObject.getString("writer");
                            String description = jsonObject.getString("description");
                            int hits = jsonObject.getInt("hits");
                            int likes = jsonObject.getInt("likes");
                            int comments = jsonObject.getInt("comments");
                            String write_date = jsonObject.getString("write_date");

                            boardAdapter.addItem(postNo, title, description, writer, write_date, hits, comments, likes);
                            boardAdapter.notifyDataSetChanged();
                            Log.d("itemadd", i + 1 + "번째 아이템이 추가되었습니다." + title + "," + description + "," + hits + "," + writer + "," + write_date +
                                    "," + likes + "," + comments + ",");
                        }

                    }
                } catch (IOException | JSONException e) { }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

   /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==300){
                Log.d("resultok","true"  );
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");
                boardAdapter.addItem(1, title, description, "bea0266","2021-01-11 11:11:11",
                        10, 5, 3);
                boardAdapter.notifyDataSetChanged();

            }
        }

    }
 */

}