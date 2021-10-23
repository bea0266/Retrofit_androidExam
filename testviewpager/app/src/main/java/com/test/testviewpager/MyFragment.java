package com.test.testviewpager;

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

import org.w3c.dom.Text;

public class MyFragment extends Fragment {

    TextView tvName;
    ImageView imgbanner;
    int num;

    public MyFragment() {


    }

    public static MyFragment newInstance(int no) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("index", no);
        Log.d("index", String.valueOf(no));
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num = getArguments().getInt("index");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_new, container, false);
       tvName = (TextView) view.findViewById(R.id.tvName);
       imgbanner = (ImageView) view.findViewById(R.id.imgBanner);

       Log.d("getIndex", String.valueOf(num));

       String text = "Hello This is page "+num;
       tvName.setText(text);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
