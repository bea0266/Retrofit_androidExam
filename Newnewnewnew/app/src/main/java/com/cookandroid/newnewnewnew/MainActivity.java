package com.cookandroid.newnewnewnew;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView list;
    PlanetAdapter adapter;
    ImageButton addMoon, addPlanet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new PlanetAdapter();
        list = (ListView) findViewById(R.id.list1);

        addPlanet = (ImageButton) findViewById(R.id.add_planet);

        addPlanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlanetInputInfo.class);
                startActivityForResult(intent,100);
            }
        });

        list.setAdapter(adapter);
        adapter.addItem("운동", "2021-01-01", "다짐", "지구");
        adapter.addItem("공부", "2021-02-01", "다짐", "지구");
        adapter.addItem("게임", "2021-03-01", "다짐", "지구");
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {

                String year = data.getStringExtra("year");
                String month = data.getStringExtra("month");
                String day = data.getStringExtra("day");
                String dajim = data.getStringExtra("dajim");
                String keyword = data.getStringExtra("keyword");
                String date = year+"-"+month+"-"+day;

                adapter.addItem(keyword, date, dajim, "화성");
                adapter.notifyDataSetChanged();


        }
    }
}