package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CalendarInfo extends AppCompatActivity {
    CalendarView calender;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        calender = findViewById(R.id.calendarView);
        calender.setOnDateChangeListener(
        new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    @NonNull CalendarView view,
                    int year,
                    int month,
                    int dayOfMonth) {
                Date = dayOfMonth + "-" + (month + 1) + "-" + year;
                Intent i = new Intent(CalendarInfo.this, LeaveDetails.class);
                i.putExtra("DATE", Date);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
