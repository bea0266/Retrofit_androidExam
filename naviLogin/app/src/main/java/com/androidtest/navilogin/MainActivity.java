package com.androidtest.navilogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBar actionBar;
    DrawerLayout drawerLayout;
    BoardFragment boardFragment;
    LoginFragment loginFragment;
    MypageFragment mypageFragment;
    HomeFragment homeFragment;
    NavigationView nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true); //버튼 이미지 불러오기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_name);
        actionBar.setTitle("홈");
        actionBar.setHomeButtonEnabled(true);

        boardFragment = BoardFragment.newInstance();
        mypageFragment = MypageFragment.newInstance();
        loginFragment = LoginFragment.newInstance();
        homeFragment = homeFragment.newInstance();

        if (nav_view != null) {
            nav_view.setNavigationItemSelectedListener(this);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main, homeFragment).commit();


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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();

        switch(id){
            case R.id.login:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_main, loginFragment).commit();
                break;
            case R.id.board:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_main, boardFragment).commit();
                break;
            case R.id.myPage:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_main, mypageFragment).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);


        return false;
    }
}