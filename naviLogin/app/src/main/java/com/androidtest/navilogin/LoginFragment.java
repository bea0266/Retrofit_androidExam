package com.androidtest.navilogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class LoginFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText etId, etPw;
    Button btnLogin;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_login, container, false);
       etId = (EditText)view.findViewById(R.id.etId);
       etPw = (EditText)view.findViewById(R.id.etPw);
       btnLogin = (Button)view.findViewById(R.id.btnLogin);

       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
               Bundle bundle = new Bundle();
               bundle.putString("id", etId.getText().toString());
               bundle.putString("password", etPw.getText().toString());

               HomeFragment fragHome =  HomeFragment.newInstance();
               fragHome.setArguments(bundle);
               transaction.replace(R.id.frame_main, fragHome);
               transaction.commit();

           }
       });

       return view;

    }


}