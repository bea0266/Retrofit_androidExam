package com.androidtest.navilogin;

import com.google.gson.annotations.SerializedName;

public class PostItem {
    @SerializedName("postNo")
    private int postNo; //글번호
    @SerializedName("title")
    private String title; //타이틀
    @SerializedName("description")
    private String description;//내용
    @SerializedName("writer")
    private String writer;//작성자
    @SerializedName("write_date")
    private String write_date;//작성날짜
    @SerializedName("hits")
    private int hits;//조회수
    @SerializedName("comments")
    private int comments;//댓글수
    @SerializedName("likes")
    private int likes;//추천수

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
