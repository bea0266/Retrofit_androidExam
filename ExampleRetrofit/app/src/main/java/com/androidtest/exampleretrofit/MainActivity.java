package com.androidtest.exampleretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final static String URL = "http://192.168.35.4:8000";
    Button btnSend, btnSelect;
    EditText etId, etPw;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend= (Button) findViewById(R.id.btnSend);
        btnSelect= (Button) findViewById(R.id.btnSelect);
        etId = (EditText) findViewById(R.id.et1);
        etPw = (EditText) findViewById(R.id.et2);
        tvResult = (TextView) findViewById(R.id.tvResult);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = getEdtText(etId);
                String userPw = getEdtText(etPw);

                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(userId);
                userInfo.setUserPw(userPw);


                
                Call<UserInfo> call = apiClient.getUserinfo(userInfo.getUserId(), userInfo.getUserPw());

                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if(response.isSuccessful())
                            tvResult.setText("요청이 정상적으로 처리되었습니다.");
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                            tvResult.setText("오류가 발생했습니다.");

                    }
                });


            }

        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = apiClient.selectUserinfo();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override

                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            tvResult.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        tvResult.setText("정상적이지 않은 동작입니다.");
                    }
                });
            }
        });


    }



    private static String getEdtText(EditText userInfo){
        String result = userInfo.getText().toString();
        return result;
    }



}