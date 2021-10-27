package com.test.routinetest2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import me.relex.circleindicator.CircleIndicator3;

public class PlanetContents extends Fragment {

    ViewPager2 viewPager2;
    MyAdapter adapter;
    CircleIndicator3 mIndicator;
    String promise, keyword;
    int top_planetPage = 3;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewGroup = inflater.inflate(R.layout.planet_contents, container, false);
        adapter = new MyAdapter(this, top_planetPage);
        viewPager2 = (ViewPager2) viewGroup.findViewById(R.id.viewpager);
        viewPager2.setAdapter(adapter);
        mIndicator = (CircleIndicator3) viewGroup.findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager2); //indicator와 뷰페이저 연결
        mIndicator.createIndicators(top_planetPage,0); // indicator 개수 설정
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL); //뷰페이저 슬라이드 방향 설정
        viewPager2.setCurrentItem(0); //현재 선택된 페이지를 설정합니다.
        viewPager2.setOffscreenPageLimit(2);// viewpager를 사용할 때 이전 혹은 다음페이지를 몇개까지 미리 로딩할지 정하는 함수이다.

        if(getArguments()!=null){
            keyword = getArguments().getString("keyword");
            promise = getArguments().getString("promise");
            adapter.setItem(keyword,promise);

        }






        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // 페이지가 변경되거나 점진적으로 스크롤될 때마다 호출되는 콜백을 추가합니다
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//현재 페이지를 스크롤할 때 호출됩니다.
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // (현재 표시되고 있는 첫 번째 페이지의 위치 인덱스입니다., 오프셋(offset)은 일반적으로 동일 오브젝트 안에서 오브젝트 처음부터 주어진 요소나 지점까지의 변위차를 나타내는 정수형이다.,위치로부터의 오프셋을 나타내는 픽셀 단위 값입니다. )
                if (positionOffsetPixels == 0) {
                    viewPager2.setCurrentItem(position);// 현재 선택된 페이지 설정
                }
            }



            @Override
            public void onPageSelected(int position) { // 페이지 선택시
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position); // 페이지가 3개니깐 0,1,2값이 반복되서 나타나야함
            }
        });



        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);




        viewPager2.setPageTransformer(new ViewPager2.PageTransformer() {// 표시/첨부된 페이지가 스크롤될 때마다 호출됩니다.
            @Override
            public void transformPage(@NonNull View page, float position) { //지정된 페이지에 속성 변환을 적용합니다.
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if(viewPager2.getOrientation() ==  ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationY(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });


        return viewGroup;
    }



}

