package com.androidtest.navilogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/posts")
    Call<ResponseBody> getPosts();

    @GET("/api/posts/{postNo}")
    Call<ResponseBody> getPostinfo(@Path("postNo") int postNo);

    @FormUrlEncoded
    @POST("/api/posts/regist")
    Call<PostItem> addPost(
            @Field("writer")String writer,
            @Field ("title")String title,
            @Field ("description")String description,
            @Field ("hits") int hits,
            @Field ("likes") int likes,
            @Field ("comments")int comments,
            @Field ("write_date")String write_date);

    @FormUrlEncoded
    @PUT("/api/posts/modify/{postNo}")
    Call<PostItem> updatePost(@Path("postNo") int postNo,
                              @Field("title") String title,
                              @Field("description") String description,
                              @Field("write_date") String write_date);

    @PUT("/api/posts/likes/{postNo}")
    Call<PostItem> addLike(@Path("postNo") int postNo);

    @PUT("/api/posts/comment/{postNo}")
    Call<PostItem> addComments(@Path("postNo")int postNo);

    @PUT("/api/posts/hits/{postNo}")
    Call<PostItem> addHits(@Path("postNo")int postNo);

    @DELETE("/api/posts/remove/{postNo}")
    Call<PostItem> deletePost(@Path("postNo")int postNo);

    @FormUrlEncoded
    @POST("/api/users/sign")
    Call<Object> signAccount(@Field("nickname") String nickname,
                             @Field("email") String email,
                             @Field("profileUrl") String profileUrl);
    @DELETE("api/users/{email}")
    Call<Object> deleteAccount(@Path("email") String email);


}
