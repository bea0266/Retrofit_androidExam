package com.test.routinetest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SatellAdapter extends BaseAdapter {

    Context context;
    ArrayList<Satell> satellList = new ArrayList<>();
    LayoutInflater inflater;

    public SatellAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return satellList.size();
    }

    @Override
    public Object getItem(int position) {
        return satellList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.row_spinner, parent, false);
            if(satellList!=null){
                //데이터세팅
                String text = satellList.get(position).getName();
                ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
            }



        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.row_spinner_dropdown, parent, false);

        }
        String text = satellList.get(position).getName();
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);




        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String name, String rule, String startDate, String endDate,
                        String startTime, String endTime, String cycle){

        Satell satell = new Satell();
        satell.setName(name);
        satell.setRule(rule);
        satell.setStartDate(startDate);
        satell.setEndDate(endDate);
        satell.setStartTime(startTime);
        satell.setEndDate(endTime);
        satell.setCycle(cycle);
        satellList.add(satell);

    }


}
