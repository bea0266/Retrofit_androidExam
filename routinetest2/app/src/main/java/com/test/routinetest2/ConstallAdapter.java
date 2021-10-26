package com.test.routinetest2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ConstallAdapter extends FragmentStateAdapter {


    int mCount;

    public ConstallAdapter(@NonNull Fragment frag, int count) {
        super(frag);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){
            case 0 :
                return new FragmentConstellation1();
            case 1 :
                return new FragmentConstellation2();
            case 2 :
                return new FragmentConstellation3();
            default :
                return new FragmentConstellation4();

        }

    }

    @Override
    public int getItemCount() { //
        return 4;
    }


}
