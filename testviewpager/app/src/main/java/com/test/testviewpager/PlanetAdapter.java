package com.test.testviewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PlanetAdapter extends FragmentStateAdapter {

    int mCount;

    public PlanetAdapter (FragmentActivity fa, int count){
        super(fa);
        mCount = count;

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
