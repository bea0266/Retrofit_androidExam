package com.test.routinetest2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class DetailAdapter extends FragmentStateAdapter {


    int mCount;
    public DetailAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new PlanetContents();

        } else {
            return new ConstallCollection();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
