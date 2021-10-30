package com.test.routinetest2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import org.apache.commons.lang3.StringUtils;


import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.util.Calendar;

public class SetSatellActivity extends Activity {

    final Calendar cal = Calendar.getInstance();

    EditText etStartDate, etStartTime, etEndDate, etEndTime, etTitle, etRule;
    TextView tvCycle;
    TextView startDateTime, endDateTime;
    ImageButton btnStartDate, btnStartTime, btnEndDate, btnEndTime;
    Integer btnWeekIDs[] = {R.id.sunday, R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday};
    Button btnWeek[] = new Button[7];
    Button btnSave;
    String dYear, dMonth, dDay;
    String cycleText = "매주 ";
    Calendar calendar = Calendar.getInstance();
    TextView habitContents;
    int sw[] = {0, 0, 0, 0, 0, 0, 0};
    String dayArray[] = {"일", "월", "화", "수", "목", "금", "토"};

    int isClickedStartdate = 0, isClickedEnddate = 0;
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            dYear = String.valueOf(year);
            dMonth = String.valueOf(month + 1);
            dDay = String.valueOf(dayOfMonth);

            if (isClickedStartdate == 1) {
                etStartDate.setText(dYear + "-" + dMonth + "-" + dDay);
            } else
                etEndDate.setText(dYear + "-" + dMonth + "-" + dDay);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.satell_setting);

        tvCycle = (TextView) findViewById(R.id.tvCycle);

        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        etEndTime = (EditText) findViewById(R.id.etEndTime);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etRule = (EditText) findViewById(R.id.simple_rule);

        btnSave = (Button) findViewById(R.id.MiniPlanetSave);
        btnStartDate = (ImageButton) findViewById(R.id.callendarStart);
        btnEndDate = (ImageButton) findViewById(R.id.callendarEnd);
        btnStartTime = (ImageButton) findViewById(R.id.clockStart);
        btnEndTime = (ImageButton) findViewById(R.id.clockEnd);

        for (int i = 0; i < btnWeekIDs.length; i++) {

            final int index;
            index = i;
            btnWeek[index] = (Button) findViewById(btnWeekIDs[index]);
        }

        for (int i = 0; i < btnWeekIDs.length; i++) {

            final int index;
            final String dayOfTheWeek;

            dayOfTheWeek = dayArray[i];
            index = i;

                    btnWeek[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                            if (sw[index] == 0) {
                                sw[index] = 1;
                                btnWeek[index].setTextColor(Color.BLACK);

                                if(cycleText.length()>=15){

                                    cycleText += dayOfTheWeek + ",";
                                    tvCycle.setText("매일");
                                }else{
                                    cycleText += dayOfTheWeek + ",";
                                    tvCycle.setText(cycleText);
                                }



                            } else {
                                sw[index] = 0;
                                btnWeek[index].setTextColor(Color.WHITE);

                                String remove = StringUtils.remove(cycleText, dayOfTheWeek + ",");
                                if(remove.length()>3){
                                    tvCycle.setText(remove);
                                    cycleText = remove;
                                } else
                                {
                                    tvCycle.setText("");
                                    cycleText = "매주 ";
                                }



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
                            if (hour < 12) {

                                msg = String.format("AM %d: %d분", hour, min);
                                etStartTime.setText(msg);
                            } else {

                                msg = String.format("오후 %d시 %d분", hour - 12, min);
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
                            if (hour < 12) {
                                msg = String.format("오전 %d시 %d분", hour, min);
                                etEndTime.setText(msg);
                            } else {

                                msg = String.format("오후 %d시 %d분", hour - 12, min);
                                etEndTime.setText(msg);
                            }
                        }
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                    dialog.show();

                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SetSatellActivity.this, DetailPlanet.class);
                    intent.putExtra("name", etTitle.getText().toString());
                    intent.putExtra("rule", etRule.getText().toString());
                    intent.putExtra("startDate", etStartDate.getText().toString());
                    intent.putExtra("endDate", etEndDate.getText().toString());
                    intent.putExtra("startTime", etStartTime.getText().toString());
                    intent.putExtra("endTime", etEndTime.getText().toString());
                    intent.putExtra("cycle", etRule.getText().toString());
                }
            });


        }

    }
