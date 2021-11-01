package com.test.routinetest2;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;
@SuppressWarnings("deprecation")
public class DetailPlanet extends FragmentActivity {

    TabLayout tabs;
    PlanetContents planetContents;
    ConstallCollection constallCollection;
    DetailAdapter adapter;
    ViewPager2 vp2;
    TextView selected;
    Button addSatell, removeSatell;
    int page_num = 2;
    CircleIndicator3 mIndicator;
    String text;
    String[] tabItem = {"행 성", "별 자 리"};
    ArrayList<String> satellName;
    FragmentTransaction ftFirst, ftSelected;
    FragmentManager fm;
    String keyword, promise;
    Spinner spinner;
    SatellAdapter sAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_planet);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        promise = intent.getStringExtra("dajim");
        satellName = new ArrayList<>();
        planetContents = new PlanetContents();
        constallCollection = new ConstallCollection();
        spinner = (Spinner) findViewById(R.id.itemSpinner);
        addSatell = (Button) findViewById(R.id.add_miniPlanet);
        removeSatell = (Button) findViewById(R.id.remove_miniPlanet);
        selected = (TextView) findViewById(R.id.selected);

        sAdapter = new SatellAdapter(this);
        spinner.setAdapter(sAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        removeSatell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addSatell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetSatellActivity.class);
                startActivityForResult(intent,100);
            }
        });


        fm = getSupportFragmentManager();

        adapter = new DetailAdapter(this,  page_num);
        tabs = (TabLayout) findViewById(R.id.tabs);
        vp2 = (ViewPager2) findViewById(R.id.vp2);

        vp2.setAdapter(adapter);


        vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL); //뷰페이저 슬라이드 방향 설정
        vp2.setCurrentItem(0); //현재 선택된 페이지를 설정합니다.
        vp2.setOffscreenPageLimit(2);// viewpager를 사용할 때 이전 혹은 다음페이지를 몇개까지 미리 로딩할지 정하는 함수이다.
        new TabLayoutMediator(tabs, vp2, (tab, position) -> tab.setText(tabItem[position])).attach();

        fm.beginTransaction().replace(R.id.frame, planetContents, "first").commitAllowingStateLoss();

        putData(keyword, promise, planetContents);



        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {

                    case 0:
                        if(fm.findFragmentByTag("first")!=null) {
                            fm.beginTransaction().show(fm.findFragmentByTag("first")).commit();
                        }

                        if(fm.findFragmentByTag("second")!=null){
                            fm.beginTransaction().hide(fm.findFragmentByTag("second")).commit();
                        }
                        break;
                    default:
                        if(fm.findFragmentByTag("second")!=null){
                            fm.beginTransaction().show(fm.findFragmentByTag("second")).commit();
                        } else {
                            fm.beginTransaction().add(R.id.frame, constallCollection, "second").commit();
                        }

                        if(fm.findFragmentByTag("first")!=null){
                            fm.beginTransaction().hide(fm.findFragmentByTag("first")).commit();
                        }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // 페이지가 변경되거나 점진적으로 스크롤될 때마다 호출되는 콜백을 추가합니다
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//현재 페이지를 스크롤할 때 호출됩니다.
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // (현재 표시되고 있는 첫 번째 페이지의 위치 인덱스입니다., 오프셋(offset)은 일반적으로 동일 오브젝트 안에서 오브젝트 처음부터 주어진 요소나 지점까지의 변위차를 나타내는 정수형이다.,위치로부터의 오프셋을 나타내는 픽셀 단위 값입니다. )
                if (positionOffsetPixels == 0) {
                    vp2.setCurrentItem(position);// 현재 선택된 페이지 설정
                }
            }


            @Override
            public void onPageSelected(int position) { // 페이지 선택시
                super.onPageSelected(position);

            }
        });


        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);


        vp2.setPageTransformer(new ViewPager2.PageTransformer() {// 표시/첨부된 페이지가 스크롤될 때마다 호출됩니다.
            @Override
            public void transformPage(@NonNull View page, float position) { //지정된 페이지에 속성 변환을 적용합니다.
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (vp2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(vp2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationY(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==100){

                String name = data.getStringExtra("name");
                String rule = data.getStringExtra("rule");
                String startDate = data.getStringExtra("startDate");
                String endDate = data.getStringExtra("endDate");
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                String cycle = data.getStringExtra("cycle");

                sAdapter.addItem(name, rule, startDate, endDate, startTime, endTime, cycle);
                sAdapter.notifyDataSetChanged();


            }



        }


    }

    public void putData(String keyword, String promise, PlanetContents pc){
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        bundle.putString("promise",promise);
        pc.setArguments(bundle);

    }
}
