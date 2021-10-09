package com.androidtest.retroboard;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIservice {

    @GET("/")
    Call<ResponseBody> selectPost();


    @FormUrlEncoded
    @POST("/inserts")
    Call<ListviewItem> sendMypost(
        @Field("title") String title,
        @Field("writer") String writer,
        @Field("description") String description,
        @Field("write_date") String write_date,
        @Field("hits") int hits
    );

    @FormUrlEncoded
    @PUT("/addHits")
    Call<ListviewItem> updateHits(
            @Field("position") int position,
            @Field("hits") int hits
    );

    @FormUrlEncoded
    @PUT("/update/{position}")
    Call<ListviewItem> updatePost(
      @Path("position") int position,
      @Field("title") String title,
      @Field("writer") String writer,
      @Field("description") String description,
      @Field("write_date") String write_date
    );

   @FormUrlEncoded
   @HTTP(method = "DELETE", hasBody = true, path="/delete/{position}")
    Call<List<ListviewItem>> deletePost(
      @Path("position") int position,
      @Field("count") int count
    );


}
