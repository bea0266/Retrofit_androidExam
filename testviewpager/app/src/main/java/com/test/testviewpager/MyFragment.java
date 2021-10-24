package com.test.testviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class MyFragment extends Fragment {

    TextView tvName;
    ImageView imgbanner;
    int textNum;


    public static MyFragment newInstance(int pos) {
        MyFragment fragment = new MyFragment();
        fragment.textNum=pos;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("lifecycle", "onCreate");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_new, container, false);
       Log.i("lifecycle", "onCreateView()");
       tvName = (TextView) view.findViewById(R.id.tvName);
       imgbanner = (ImageView) view.findViewById(R.id.imgBanner);

       return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("lifecycle", "onpause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("lifecycle", "onViewCreated()");


    }
}
