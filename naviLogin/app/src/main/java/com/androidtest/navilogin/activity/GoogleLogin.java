package com.androidtest.navilogin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidtest.navilogin.GlobalApplication;
import com.androidtest.navilogin.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;
/*
 google login class
 */
public class GoogleLogin extends Activity {
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleClient;
    private FirebaseAuth mGoogleLoginModule;
    private MainActivity mainActivity;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleLoginModule = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        if (mGoogleLoginModule.getCurrentUser() == null) {
            Intent signInIntent = mGoogleClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        } else if (mGoogleLoginModule.getCurrentUser() != null) {
            List<String> userInfo = new ArrayList<>();
            GlobalApplication mGlobalApplication = (GlobalApplication) getApplication();
            userInfo.add(String.format("%s-%s", "GOOGLE", mGoogleLoginModule.getCurrentUser().getUid()));
            userInfo.add(mGoogleLoginModule.getCurrentUser().getDisplayName());
            GlobalApplication.setGlobalUserLoginInfo(userInfo);
            Intent intent = new Intent(GoogleLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(getApplicationContext(), "Google Sign In Failed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GoogleLogin.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mGoogleLoginModule.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                            if (mGoogleLoginModule.getCurrentUser() != null) {
                                List<String> userInfo = new ArrayList<>();
                                GlobalApplication mGlobalHelper = (GlobalApplication) getApplication();
                                userInfo.add(mGoogleLoginModule.getCurrentUser().getUid());
                                userInfo.add(mGoogleLoginModule.getCurrentUser().getDisplayName());
                                GlobalApplication.setGlobalUserLoginInfo(userInfo);
                                Intent intent = new Intent(GoogleLogin.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Google Authentication Failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}