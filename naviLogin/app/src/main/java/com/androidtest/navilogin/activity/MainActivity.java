package com.androidtest.navilogin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.androidtest.navilogin.ApiService;
import com.androidtest.navilogin.UserInfo;
import com.androidtest.navilogin.fragment.BoardFragment;
import com.androidtest.navilogin.fragment.HomeFragment;
import com.androidtest.navilogin.fragment.MypageFragment;
import com.androidtest.navilogin.R;
import com.androidtest.navilogin.fragment.SettingsFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBar actionBar;
    public static UserInfo userInfo = new UserInfo();
    RelativeLayout relative;
    DrawerLayout drawerLayout;

    BoardFragment boardFragment;
    MypageFragment mypageFragment;
    HomeFragment homeFragment;
    SettingsFragment settingsFragment;

    NavigationView nav_view;
    View header;
    TextView headNick;
    ImageView proImg;
    static boolean isLogin=false;
    static String activityNow = "home";

    ApiService apiService = createRetrofit();




    public static ApiService createRetrofit(){
        String url = "http://172.16.61.106:3030";
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        header = nav_view.getHeaderView(0);

        proImg = (ImageView)header.findViewById(R.id.imageView);
        headNick = (TextView) header.findViewById(R.id.tvNickname);
        relative = (RelativeLayout)header.findViewById(R.id.relative);

        getHashKey();
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true); //?????? ????????? ????????????
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name);
        actionBar.setTitle("???");
        actionBar.setHomeButtonEnabled(true);

        boardFragment = BoardFragment.newInstance();
        mypageFragment = MypageFragment.newInstance();
        homeFragment = homeFragment.newInstance();
        settingsFragment = settingsFragment.newInstance();

        if (nav_view != null) {
            nav_view.setNavigationItemSelectedListener(this);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main, homeFragment).commit();

        if(headNick.getText().equals("?????????\n????????????.")){
            isLogin=false;
            relative.setClickable(true);
        }


        getHashKey();



            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(isLogin==false) {
                        Log.d("isclicklistener", Boolean.toString(isLogin));
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(intent, 100);
                    } else {
                        relative.setClickable(false);
                        Log.d("isclicklistener", Boolean.toString(isLogin));

                    }
                }
            });




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
      switch (item.getItemId())

      {
         case android.R.id.home:{
             drawerLayout.openDrawer(GravityCompat.START);
             return true;
         }

         default:
             return super.onOptionsItemSelected(item);
      }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==100){

                isLogin = true;
                long userId = data.getLongExtra("userId",0);
                String nickname = data.getStringExtra("nickname");
                String profileImg = data.getStringExtra("profileImg");
                String email = data.getStringExtra("email");
                userInfo.setNickname(nickname);
                userInfo.setProfileUrl(profileImg);
                userInfo.setEmail(email);
                userInfo.setUserId(userId);
                Call<UserInfo> call = apiService.signAccount(userId, nickname,email, profileImg);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        Log.d("success", "????????? ??????");

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e("error", t.getMessage());

                    }
                });



                headNick.setText(nickname+"???\n???????????????.");

                Glide.with(this)
                        .load(profileImg)
                        .override(100,100)
                        .into(proImg);


            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        switch(id){
            case R.id.board:
                if(isLogin==true){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, boardFragment).commit(); //?????? ?????????????????? ?????????.
                    actionBar.setTitle("?????????"); //????????? ??????
                    activityNow="board";
                    drawer.closeDrawer(GravityCompat.START); //???????????? ??????
                    break;
                } else { //???????????? ???????????? ?????? ??????
                    Toast.makeText(getApplicationContext(), "????????? ??? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.myPage:
                if(isLogin==true){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, mypageFragment).commit();
                    actionBar.setTitle("???????????????");
                    drawer.closeDrawer(GravityCompat.START);
                    activityNow="mypage";
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), "????????? ??? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.setting:

                if(isLogin==true){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, settingsFragment).commit();
                    actionBar.setTitle("??????");
                    activityNow="setting";
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    Toast.makeText(getApplicationContext(), "????????? ??? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                }



        }



        return false;
    }
    private long lastTimeBackPressed; //???????????? ????????? ????????? ??????


    @Override
    public void onBackPressed() {

        if (activityNow.equals("home")) {
            //2??? ????????? ???????????? ????????? ??? ?????? ??? ??? ??????
            if (System.currentTimeMillis() - lastTimeBackPressed < 2000) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            //'??????' ?????? ?????? ?????? ??? ?????????
            Toast.makeText(this, "'??????' ????????? ?????? ??? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
            //lastTimeBackPressed??? '??????'????????? ?????? ????????? ??????
            lastTimeBackPressed = System.currentTimeMillis();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, homeFragment).commit();
            activityNow="home";

        }
    }
    public static long getUserId() {
        return userInfo.getUserId();
    }
    public static String getUserName() {
        return userInfo.getNickname();
    }
    public static String getUserEmail(){
        return userInfo.getEmail();
    }
    public static String getThumbnailUrl(){
        return userInfo.getProfileUrl();
    }
    private void getHashKey(){ //????????? ????????? ?????????
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