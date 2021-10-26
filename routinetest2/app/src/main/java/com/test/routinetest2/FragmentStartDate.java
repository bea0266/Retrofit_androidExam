package com.test.routinetest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentStartDate extends Fragment {

    Chronometer chrono;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewStartDate = (ViewGroup) inflater.inflate(R.layout.fragment_viewpager2_start_date, container, false);
        chrono = (Chronometer) viewStartDate.findViewById(R.id.chrono);
        chrono.start();

        return viewStartDate;
    }

    public void onDestroy(){

        super.onDestroy();
        chrono.stop();

    }




}
