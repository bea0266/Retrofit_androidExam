package com.androidtest.exampleretrofit;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("userId")
    private String userId;
    @SerializedName("userPw")
    private String userPw;

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
}
