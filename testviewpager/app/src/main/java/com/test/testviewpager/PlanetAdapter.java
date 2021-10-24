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

        switch (position)
        {
            case 0:
                return new Planet1();
            case 1:
                return new Planet2();
            default:
                return new Planet3();

        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
