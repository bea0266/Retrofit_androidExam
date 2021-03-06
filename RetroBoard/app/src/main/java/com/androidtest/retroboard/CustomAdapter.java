package com.androidtest.retroboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<ListviewItem> listviewItemList = new ArrayList<ListviewItem>();
    @Override
    public int getCount() {
        return listviewItemList.size();
    }

    @Override
    public ListviewItem getItem(int position) {
        return listviewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView hitsTextView = (TextView) convertView.findViewById(R.id.tvHits);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView writerTextView = (TextView) convertView.findViewById(R.id.tvWriter);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.tvDate);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListviewItem listviewItem = listviewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        hitsTextView.setText("조회수 "+Integer.toString(listviewItem.getHits()));
        titleTextView.setText(listviewItem.getTitle());
        writerTextView.setText(listviewItem.getWriter());
        dateTextView.setText(listviewItem.getWrite_date());

        return convertView;
    }
    public void swapItem(int cnt){
/*
//커밋 이후 작성할 코드
        for(int i=cnt-1; i>0; i-- )
        Collections.swap(listviewItemList, i, i-1 );
        for(int i=0; i<listviewItemList.size(); i++) {
            Log.d("listorder", "listorder[" + i + "]" + listviewItemList.get(i).getTitle());
        }
*/
    }
    public void removeItem(int position){
        listviewItemList.remove(position);
    }
    public void addItem(String title,  String description, int hits, String writer, String write_date){
        ListviewItem item = new ListviewItem();

        item.setWrite_date(write_date);
        item.setDescription(description);
        item.setWriter(writer);
        item.setHits(hits);
        item.setTitle(title);

        listviewItemList.add(item);
    }
}
