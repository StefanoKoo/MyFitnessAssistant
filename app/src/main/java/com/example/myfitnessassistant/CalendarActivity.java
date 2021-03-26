package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import data.MyEventDay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements OnDayClickListener, View.OnClickListener {
    private static String TAG = "CalendarActivity";

    private static Integer REQUEST_MAKE_ROUTINE = 0;

    CalendarView mCalendarView;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private EventDay mDate;
    private TextView mTextView;
    List<EventDay> mEventDays = new ArrayList<>();
    List<MyEventDay> mMyEventDays = new ArrayList<>();

    MyEventDay mEventDay;

    private long backKeyPressedTime = 0;


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

        mTextView = findViewById(R.id.show_event_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventDay eventDay = new EventDay(mCalendarView.getFirstSelectedDate());
//        Toast.makeText(this,getFormattedDate(eventDay.getCalendar().getTime()),Toast.LENGTH_SHORT).show();
        if(eventDay instanceof MyEventDay) {
            Log.d(TAG,"onResume");
//            Toast.makeText(this,((MyEventDay) eventDay).getNote(),Toast.LENGTH_SHORT).show();
            mTextView.setText(((MyEventDay) eventDay).getNote());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_calendar, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        // Event 가 있을 때
        if (mEventDays.contains(eventDay)) {
//            Toast.makeText(this,"Event Exists",Toast.LENGTH_SHORT).show();
            // event 의 노트를 가져오는 방법
            if(eventDay instanceof MyEventDay) {
                Toast.makeText(this,((MyEventDay) eventDay).getNote(),Toast.LENGTH_SHORT).show();
                mTextView.setText(((MyEventDay) eventDay).getNote());
            }
        }
        // Event 가 없을 때
        else {
//            Toast.makeText(this,"No Events",Toast.LENGTH_SHORT).show();
            mTextView.setText("No Events Today");
        }
//        mEventDay = new MyEventDay(eventDay.getCalendar(),R.drawable.ic_dumbell_15,"Test");
//        mEventDays.add(mEventDay);
//        mCalendarView.setEvents(mEventDays);
    }

    @Override
    public void onClick(View view) {
//        Calendar mCalendar = mDate.getCalendar();
//        mEventDays.add(new EventDay(mCalendar,R.drawable.ic_dumbell_15));
//        Drawable textDrawable = CalendarUtils.getDrawableText(this,"Test123456789", Typeface.SANS_SERIF, R.color.maColor,10);
//        Drawable textDrawable = MyCalendarUtils.getDrawableText(this,"Test",Typeface.SANS_SERIF,R.color.maColor,9);
//        mEventDays.add(new EventDay(mCalendar,textDrawable));
//        mCalendarView.setEvents(mEventDays);
//        Toast.makeText(this,getFormattedDate(mCalendarView.getFirstSelectedDate().getTime()),Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,mEventDay.getNote(),Toast.LENGTH_SHORT).show();

        MyEventDay today = new MyEventDay(mCalendarView.getFirstSelectedDate(),R.drawable.ic_dumbell_15,"Test");

        Intent intent = new Intent(this, WorkoutListActivity.class);
        intent.putExtra("Test Item",today);
        startActivityForResult(intent,REQUEST_MAKE_ROUTINE);
        overridePendingTransition(R.anim.slide_enter,R.anim.slide_exit);
    }

    private static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_MAKE_ROUTINE) {
            if (resultCode == RESULT_OK) {
                MyEventDay today = new MyEventDay(mCalendarView.getFirstSelectedDate(),R.drawable.ic_dumbell_15,"Test");
                mEventDays.add(today);
                mCalendarView.setEvents(mEventDays);

                mTextView.setText(today.getNote());
               // TODO : DB에 넣기
            }
        }
    }
}