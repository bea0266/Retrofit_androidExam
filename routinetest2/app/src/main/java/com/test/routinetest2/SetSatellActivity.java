package com.test.routinetest2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class SetSatellActivity extends Activity {

    final Calendar cal = Calendar.getInstance();

    EditText etStartDate, etStartTime, etEndDate, etEndTime;
    TextView habitTitle;
    TextView startDateTime, endDateTime;
    ImageButton btnStartDate, btnStartTime, btnEndDate, btnEndTime;
    Integer btnWeekIDs[] = {R.id.sunday, R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday};
    Button btnWeek[] = new Button[7];
    String dYear, dMonth, dDay;
    Calendar calendar = Calendar.getInstance();
    TextView habitContents;
    int sw[] = {0,0,0,0,0,0,0};
    int isClickedStartdate = 0, isClickedEnddate = 0, isClickedStarttime = 0, isClickedEndtime=0;
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            dYear = String.valueOf(year);
            dMonth = String.valueOf(month+1);
            dDay = String.valueOf(dayOfMonth);

            if (isClickedStartdate==1){
                etStartDate.setText(dYear+"-"+ dMonth +"-"+ dDay);
            } else
                etEndDate.setText(dYear+"-"+ dMonth +"-"+ dDay);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.satell_setting);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        etEndTime = (EditText) findViewById(R.id.etEndTime);

        btnStartDate = (ImageButton) findViewById (R.id.callendarStart);
        btnEndDate = (ImageButton) findViewById (R.id.callendarEnd);
        btnStartTime = (ImageButton) findViewById(R.id.clockStart);
        btnEndTime = (ImageButton) findViewById(R.id.clockEnd);

        for (int i = 0; i < btnWeekIDs.length; i++) {

            final int index;
            index = i;
            btnWeek[index] = (Button) findViewById(btnWeekIDs[index]);
        }

        for (int i= 0; i<btnWeekIDs.length; i++){

            final int index;
            index = i;
            btnWeek[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sw[index]==0){
                        sw[index]=1;
                        btnWeek[index].setTextColor(Color.BLACK);
                    } else{
                        sw[index]=0;
                        btnWeek[index].setTextColor(Color.WHITE);

                    }
                }
            });

        }





        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickedStartdate = 1;
                isClickedEnddate = 0;
                new DatePickerDialog(SetSatellActivity.this, myDatePicker,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickedEnddate = 1;
                isClickedStartdate = 0;
                new DatePickerDialog(SetSatellActivity.this, myDatePicker,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(SetSatellActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        String msg;
                        if(hour<12){
                            msg = String.format("오전 %d시 %d분", hour, min);
                            etStartTime.setText(msg);
                        } else {

                            msg = String.format("오후 %d시 %d분", hour-12, min);
                            etStartTime.setText(msg);
                        }

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(SetSatellActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg;
                        if(hour<12){
                            msg = String.format("오전 %d시 %d분", hour, min);
                            etEndTime.setText(msg);
                        } else {

                            msg = String.format("오후 %d시 %d분", hour-12, min);
                            etEndTime.setText(msg);
                        }
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();

            }
        });


    }


}
