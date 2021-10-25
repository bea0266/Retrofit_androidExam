package com.test.routinetest2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailPlanet.class);
                String keyword = adapter.getItem(position).getKeyword();
                String dajim = adapter.getItem(position).getDajim();
                intent.putExtra("keyword", keyword);
                intent.putExtra("dajim", dajim);
                startActivity(intent);
            }
        });

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
            String date = year+"-"+ month +"-" + day;
            Log.d("date", date);

            adapter.addItem(keyword, date, dajim, "화성");
            adapter.notifyDataSetChanged();


        }
    }
}