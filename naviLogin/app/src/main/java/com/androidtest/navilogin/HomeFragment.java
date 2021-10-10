package com.androidtest.navilogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView tvId, tvPw;
    String id, pw;


    public HomeFragment() {
        // Required empty public constructor
    }



    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvId = (TextView) view.findViewById(R.id.tvId);
        tvPw = (TextView) view.findViewById(R.id.tvPw);

        if(getArguments()!=null){

            id = getArguments().getString("id");
            pw = getArguments().getString("password");

            tvId.setText(id);
            tvPw.setText(pw);
        }

        return view;
    }
}