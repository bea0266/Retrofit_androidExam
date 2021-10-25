package com.test.routinetest2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.kakao.auth.Session;
import androidx.annotation.Nullable;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity {

    private LoginButton kakaoBtn; //카카오 로그인 버튼
    private KakaoLogin.KakaoSessionCallback sessionCallback;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getHashKey();

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


    public void directToSecondActivity(Boolean result, UserAccount kakaoAccount, MeV2Response response) {

        if (result) {
            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
            // 사용자 정보 요청 (기본)
//            Profile profile = kakaoAccount.getProfile();
//            String kakaoNick = profile.getNickname();
//            String kakaoImg = profile.getThumbnailImageUrl();
//            String kakaoEmail = kakaoAccount.getEmail();
//            long kakaoId = response.getId();
//
//            intent.putExtra("userId", kakaoId);
//            intent.putExtra("nickname", kakaoNick);
//            intent.putExtra("profileImg", kakaoImg);
//            intent.putExtra("email", kakaoEmail);
//
//
//            setResult(RESULT_OK, intent);
//            Log.d("userId", Long.toString(kakaoId));
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void getHashKey(){ //해쉬키 구하는 메서드
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
