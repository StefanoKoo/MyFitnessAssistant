package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements OnDayClickListener, View.OnClickListener {

    CalendarView mCalendarView;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private EventDay mDate;
    private Button mTestButton;
    List<EventDay> mEventDays = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_account_circle_20);

        mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setOnDayClickListener(this::onDayClick);
        findViewById(R.id.testButton).setOnClickListener(this::onClick);
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        mDate = eventDay;
//        Toast.makeText(this,getFormattedDate(eventDay.getCalendar().getTime()),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Calendar mCalendar = mDate.getCalendar();
        mEventDays.add(new EventDay(mCalendar,R.drawable.ic_baseline_eco_5));
        mCalendarView.setEvents(mEventDays);
        Toast.makeText(this,getFormattedDate(mCalendarView.getFirstSelectedDate().getTime()),Toast.LENGTH_SHORT).show();
    }

    private static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

}