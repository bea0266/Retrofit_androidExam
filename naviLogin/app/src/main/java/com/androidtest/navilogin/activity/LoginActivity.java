package com.androidtest.navilogin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidtest.navilogin.R;

import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.usermgmt.response.model.UserProfile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity {

    private LoginButton kakaoBtn; //카카오 로그인 버튼
    private KakaoLogin.KakaoSessionCallback sessionCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        kakaoBtn = findViewById(R.id.kakaoBtn);

        kakaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoBtn.performClick();
            }
        });

        //getHashKey();
        if (!HasKakaoSession()) {
            sessionCallback = new KakaoLogin.KakaoSessionCallback(getApplicationContext(), LoginActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
        } else if (HasKakaoSession()) {
            sessionCallback = new KakaoLogin.KakaoSessionCallback(getApplicationContext(), LoginActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
            Session.getCurrentSession().checkAndImplicitOpen();
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private boolean HasKakaoSession() {
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            return false;
        }
        return true;
    }


    public void directToSecondActivity(Boolean result, UserAccount kakaoAccount) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (result) {
            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
            // 사용자 정보 요청 (기본)
            Profile profile = kakaoAccount.getProfile();
            String kakaoNick = profile.getNickname();
            String kakaoImg = profile.getThumbnailImageUrl();


            intent.putExtra("nickname", kakaoNick);
            intent.putExtra("profileImg", kakaoImg);
            setResult(RESULT_OK, intent);
            finish();
        }
    }



    }
