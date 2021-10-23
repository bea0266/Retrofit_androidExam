package com.test.testviewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentStateAdapter {
    public int mCount;
    ArrayList<Fragment> fragList = new ArrayList<>(); // 프래그먼트 객체를 저장할 리스트





    public MyAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;
    }
    @Override
    public Fragment createFragment(int position){
        int index = getRealPosition(position);

        if(index==0) return new FragmentFirst();
        else if(index==1) return new FragmentSecond();
        else if(index==2) return new FragmentThird();
        else if(index==3) return new FragmentFourth();
        else if(index==4) return new FragmentFifth();
        else return new MyFragment();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position){return position % mCount;}
}
