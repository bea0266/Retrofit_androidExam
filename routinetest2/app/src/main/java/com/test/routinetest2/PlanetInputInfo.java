package com.test.routinetest2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class PlanetInputInfo extends Activity {
    EditText edtTitleContents, memoEdit;
    ImageButton imgBtnCalendarSetting;
    String dYear, dMonth, dDay;
    Button btnSave;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            dYear = String.valueOf(year);
            dMonth = String.valueOf(month+1);
            dDay = String.valueOf(dayOfMonth);




        }

    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_info);

        edtTitleContents = (EditText) findViewById(R.id.edtTitleContents);
        memoEdit = (EditText) findViewById(R.id.Memo_edit);
        imgBtnCalendarSetting = (ImageButton) findViewById(R.id.imgBtnCalendarSetting);
        btnSave = (Button) findViewById(R.id.btnSave);



        imgBtnCalendarSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(PlanetInputInfo.this, myDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dajim = memoEdit.getText().toString();
                String keyword = edtTitleContents.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("year",dYear);
                intent.putExtra("month",dMonth);
                intent.putExtra("day",dDay);
                intent.putExtra("dajim", dajim);
                intent.putExtra("keyword", keyword);

                setResult(RESULT_OK, intent);
                finish();





            }
        });



    }
}
