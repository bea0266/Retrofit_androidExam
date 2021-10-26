package com.test.routinetest2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;
    FragmentPlanet fragPlanet = new FragmentPlanet();
    FragmentMyPromise fragMypromise = new FragmentMyPromise();
    FragmentStartDate fragStartDate = new FragmentStartDate();
    public MyAdapter(@NonNull Fragment frag, int count) {
        super(frag);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {


        if (position == 0) return fragPlanet;
        else if (position == 1) return fragMypromise;
        else return fragStartDate;
    }

    public void setItem(String keyword, String dajim){
        fragPlanet.setKeyword(keyword);
        fragMypromise.setPromise(dajim);
    }

    @Override
    public int getItemCount() { //
        return 3;
    }


}
