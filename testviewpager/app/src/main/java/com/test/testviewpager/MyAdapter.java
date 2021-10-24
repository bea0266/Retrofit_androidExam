package com.test.testviewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentStateAdapter {
    public int mCount;
    ArrayList<Fragment> fragList = new ArrayList<>(); // 프래그먼트 객체를 저장할 리스트





    public MyAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;
    }
    @NonNull
    @Override
    public MyFragment createFragment(int position) {
        int index = getRealPosition(position);
        return MyFragment.newInstance(index);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public int getRealPosition(int position){return position % mCount;}


}
