package com.androidtest.exampleretrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("/send")
    Call<UserInfo> getUserinfo(
            @Query("userId") String userId,
            @Query("userPw") String userPw
    );
    
    @GET("/posts")
    Call<ResponseBody> selectUserinfo();


}
