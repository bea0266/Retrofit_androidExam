package com.androidtest.navilogin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/posts")
    Call<ResponseBody> getPosts();

    @GET("/api/posts/{postNo}")
    Call<ResponseBody> getPostinfo(@Path("postNo") int postNo);

    @FormUrlEncoded
    @POST("/api/posts")
    Call<PostItem> addPost(
            @Field("userId") long userId,
            @Field("writer")String writer,
            @Field ("title")String title,
            @Field ("description")String description,
            @Field ("hits") int hits,
            @Field ("likes") int likes,
            @Field ("comments")int comments,
            @Field ("write_date")String write_date);

    @FormUrlEncoded
    @PUT("/api/posts/{postNo}")
    Call<PostItem> updatePost(
            @Path("postNo") int postNo,
            @Field("title") String title,
            @Field("description") String description,
            @Field("write_date") String write_date);

    @PUT("/api/posts/{postNo}/likes")
    Call<PostItem> addLike(@Path("postNo") int postNo);

    @PUT("/api/posts/{postNo}/comments")
    Call<PostItem> addComments(@Path("postNo")int postNo);

    @PUT("/api/posts/{postNo}/hits")
    Call<PostItem> addHits(@Path("postNo")int postNo);


    @DELETE("/api/posts/{postNo}")
    Call<PostItem> deletePost(@Path("postNo")int postNo);

    @FormUrlEncoded
    @POST("/api/users")
    Call<UserInfo> signAccount(
            @Field("userId") long userId,
            @Field("nickname") String nickname,
            @Field("email") String email,
            @Field("profileUrl") String profileUrl);

    @DELETE("/api/users/{userId}")
    Call<JsonObject> deleteAccount(
            @Path("userId") long userId);
}
