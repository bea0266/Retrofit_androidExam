package com.test.routinetest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class FragmentPlanet extends Fragment {
    TextView tvKeyword;
    private String keyword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewPlanet = (ViewGroup) inflater.inflate(R.layout.fragment_viewpager2_planet, container, false);
        tvKeyword = (TextView) viewPlanet.findViewById(R.id.KeyWord);
        tvKeyword.setText(keyword);
        return viewPlanet;
    }

    public TextView getTvKeyword() {
        return tvKeyword;
    }

    public void setTvKeyword(TextView tvKeyword) {
        this.tvKeyword = tvKeyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
