package com.androidtest.navilogin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

public class KakaoLogin extends Activity {
    public static class KakaoSessionCallback implements ISessionCallback {
        private Context mContext;
        private LoginActivity loginActivity;

        public KakaoSessionCallback(Context context, LoginActivity activity) {
            this.mContext = context;
            this.loginActivity = activity;
        }

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(mContext, "KaKao 로그인 오류가 발생했습니다. " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        protected void requestMe() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                }

                @Override
                public void onSuccess(MeV2Response result) {

                    UserAccount kakaoAccount = result.getKakaoAccount();


                    loginActivity.directToSecondActivity(true, kakaoAccount, result);
                }
            });
        }
    }
}