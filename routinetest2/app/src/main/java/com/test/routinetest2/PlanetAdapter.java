package com.test.routinetest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanetAdapter extends BaseAdapter {
    ArrayList<Planet> pList = new ArrayList<>();

    @Override
    public int getCount() {
        return pList.size();
    }

    @Override
    public Planet getItem(int position) {
        return pList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.planet_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView keyword = (TextView) convertView.findViewById(R.id.keyword) ;
        TextView startDate = (TextView) convertView.findViewById(R.id.startDate) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Planet planet = pList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        keyword.setText(planet.getKeyword());
        startDate.setText(planet.getStartDate());

        return convertView;
    }

    public void addItem(String keyword, String startDate, String dajim, String planetName){

        Planet planet = new Planet();
        planet.setKeyword(keyword);
        planet.setStartDate(startDate);
        planet.setDajim(dajim);
        planet.setPlanetName(planetName);

        pList.add(planet);

    }
}
