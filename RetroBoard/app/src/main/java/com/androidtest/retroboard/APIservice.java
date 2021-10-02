package com.androidtest.retroboard;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIservice {
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
}
