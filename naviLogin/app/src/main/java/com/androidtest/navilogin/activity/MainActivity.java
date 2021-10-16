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


    final static String URL = "http://192.168.35.4:3030";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);


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

        actionBar.setDisplayHomeAsUpEnabled(true); //버튼 이미지 불러오기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name);
        actionBar.setTitle("홈");
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

        if(headNick.getText().equals("로그인\n하십시오.")){
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
                        Log.d("success", "로그인 성공");

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e("error", t.getMessage());

                    }
                });



                headNick.setText(nickname+"님\n어서오세요.");

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
                            .replace(R.id.frame_main, boardFragment).commit(); //해당 프래그먼트를 출력함.
                    actionBar.setTitle("게시판"); //타이틀 변경
                    activityNow="board";
                    drawer.closeDrawer(GravityCompat.START); //드로어를 닫음
                    break;
                } else { //로그아웃 상태에선 사용 불가
                    Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.myPage:
                if(isLogin==true){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, mypageFragment).commit();
                    actionBar.setTitle("마이페이지");
                    drawer.closeDrawer(GravityCompat.START);
                    activityNow="mypage";
                    break;
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.setting:

                if(isLogin==true){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_main, settingsFragment).commit();
                    actionBar.setTitle("설정");
                    activityNow="setting";
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show();
                }



        }



        return false;
    }
    private long lastTimeBackPressed; //뒤로가기 버튼이 클릭된 시간


    @Override
    public void onBackPressed() {

        if (activityNow.equals("home")) {
            //2초 이내에 뒤로가기 버튼을 재 클릭 시 앱 종료
            if (System.currentTimeMillis() - lastTimeBackPressed < 2000) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            //'뒤로' 버튼 한번 클릭 시 메시지
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
            //lastTimeBackPressed에 '뒤로'버튼이 눌린 시간을 기록
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