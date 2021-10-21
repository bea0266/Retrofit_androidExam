package com.androidtest.navilogin;

import com.google.gson.annotations.SerializedName;

public class CommentItem {
    @SerializedName("postNo")
    private int postNo; //글 번호 외래키
    @SerializedName("commNo")
    private int commNo; // 댓글 번호
    @SerializedName("commWriter")
    private String commWriter; //댓글 작성자
    @SerializedName("contents")
    private String contents; // 내용
    @SerializedName("commWriteDate")
    private String commWriteDate;//댓글 작성 날짜

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public int getCommNo() {
        return commNo;
    }

    public void setCommNo(int commNo) {
        this.commNo = commNo;
    }

    public String getCommWriter() {
        return commWriter;
    }

    public void setCommWriter(String commWriter) {
        this.commWriter = commWriter;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCommWriteDate() {
        return commWriteDate;
    }

    public void setCommWriteDate(String commWriteDate) {
        this.commWriteDate = commWriteDate;
    }
}
