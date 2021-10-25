package com.test.routinetest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMyPromise extends Fragment {
    TextView tvPromise;
    String promise;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewMyPromise = (ViewGroup) inflater.inflate(R.layout.fragment_viewpager2_my_promise, container, false);
        tvPromise = (TextView) viewMyPromise.findViewById(R.id.promise);
        tvPromise.setText(promise);
        return viewMyPromise;
    }

    public TextView getTvPromise() {
        return tvPromise;
    }

    public void setTvPromise(TextView tvPromise) {
        this.tvPromise = tvPromise;
    }

    public String getPromise() {
        return promise;
    }

    public void setPromise(String promise) {
        this.promise = promise;
    }
}
