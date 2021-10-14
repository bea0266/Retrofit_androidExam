package com.androidtest.navilogin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidtest.navilogin.PostItem;
import com.androidtest.navilogin.R;
import com.androidtest.navilogin.activity.WriteboxActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    BoardAdapter boardAdapter;

    public BoardFragment() {

    }


    public static BoardFragment newInstance() {
        BoardFragment fragment = new BoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewgroup =(ViewGroup)inflater.inflate(R.layout.fragment_board, container, false);

        Button writeBtn = (Button)viewgroup.findViewById(R.id.btnWrite);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteboxActivity.class);
                startActivityForResult(intent,300);
            }
        });

        ArrayList<PostItem> list = new ArrayList<>();



        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = (RecyclerView)viewgroup.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

        boardAdapter = new BoardAdapter(list);
        recyclerView.setAdapter(boardAdapter);





        return viewgroup;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==300){
                Log.d("resultok","true"  );
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");
                boardAdapter.addItem(1, title, description, "bea0266","2021-01-11 11:11:11",
                        10, 5, 3);
                boardAdapter.swapItem(boardAdapter.getItemCount());
                boardAdapter.notifyDataSetChanged();

            }
        }

    }
}