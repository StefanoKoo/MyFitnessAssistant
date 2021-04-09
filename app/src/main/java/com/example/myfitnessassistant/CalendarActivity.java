package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import data.DateWorkout;
import data.DateWorkoutDatabase;
import data.Workout;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements OnDayClickListener, View.OnClickListener {
    private static String TAG = "CalendarActivity";

    private static Integer REQUEST_MAKE_ROUTINE = 0;

    CalendarView mCalendarView;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private TextView mTextView;
    List<EventDay> mEventDays = new ArrayList<>();

    private long backKeyPressedTime = 0;

    DateWorkoutDatabase db2;


    // TODO - TextView 가 잘 반영되지 않는 문제가 있음
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Log.d(TAG,"onCreate");

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


        db2 = Room.databaseBuilder(this,DateWorkoutDatabase.class,"DB_Workout").allowMainThreadQueries().build();
    }

    // TODO - 추후 DB 에 날짜를 Date 에서 Month / Weak / Day 로 Column 저장
    // TODO - Code Refactoring
   @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        mEventDays.clear();
        mCalendarView.setEvents(mEventDays);
        List<DateWorkout> allDateWorkouts = db2.DateWorkoutDao().getAll();
        Log.d("onResume2",allDateWorkouts.toString());

        for (DateWorkout dateWorkout:allDateWorkouts) {
            String from = dateWorkout.getDate();

            SimpleDateFormat transFormat = new SimpleDateFormat("dd MMMM yyyy");
            try {
                Date to = transFormat.parse(from);
                Calendar cal = Calendar.getInstance();
                cal.setTime(to);
                EventDay day = new EventDay(cal,R.drawable.ic_dumbell_15);
                mEventDays.add(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mCalendarView.setEvents(mEventDays);
        EventDay eventDay;
        DateWorkout test = db2.DateWorkoutDao().getDateWorkoutByDate(getFormattedDate(mCalendarView.getFirstSelectedDate().getTime()));
        if (test != null) {
            if (test.getWorkouts().size() != 0) {
                eventDay = new EventDay(mCalendarView.getFirstSelectedDate(),R.drawable.ic_dumbell_15);
                mEventDays.add(eventDay);
                mCalendarView.setEvents(mEventDays);
            }
            else {
                eventDay = new EventDay(mCalendarView.getFirstSelectedDate());
            }

            ArrayList<Workout> mWorkouts = db2.DateWorkoutDao().getDateWorkoutByDate(getFormattedDate(eventDay.getCalendar().getTime())).getWorkouts();
            if ( mWorkouts!= null) {
                String summary = "";
                for (Workout mWorkout:mWorkouts) {
                    summary += mWorkout.getWorkoutName() + " : " +
                            mWorkout.getWorkoutWeight() + "kg " +
                            mWorkout.getWorkoutSets() + " X " + mWorkout.getWorkoutReps() + "\n";
                }
                mTextView.setText(summary);
            }
        }
        else {
            mTextView.setText("No Events Today");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.database:
                Intent intent = new Intent(this, WorkoutNameListActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        Log.d(TAG,"onDayClick");
        // Event 가 있을 때
        if (mEventDays.contains(eventDay)) {
            // event 의 노트를 가져오는 방법
            mTextView.setText(workoutsToString(db2.DateWorkoutDao().getDateWorkoutByDate(getFormattedDate(eventDay.getCalendar().getTime())).getWorkouts()));
        }
        // Event 가 없을 때
        else {
            mTextView.setText("No Events Today");
        }
    }

    // 'ADD SCHEDULE' 버튼 클릭 시 진입
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, WorkoutListActivity.class);
        intent.putExtra("Date",getFormattedDate(mCalendarView.getFirstSelectedDate().getTime()));
        startActivityForResult(intent,REQUEST_MAKE_ROUTINE);
        overridePendingTransition(R.anim.slide_enter,R.anim.slide_exit);
    }

    private static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    @Override
    public void onBackPressed() {
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
                String note = data.getStringExtra("workouts_summary");
                EventDay today = new EventDay(mCalendarView.getFirstSelectedDate());
                mEventDays.add(today);
                mCalendarView.setEvents(mEventDays);
            }
        }
    }

    protected String workoutsToString(ArrayList<Workout> workouts) {
        String string = "";
        for (Workout mWorkout:workouts) {
            string += mWorkout.getWorkoutName() + " : " +
                    mWorkout.getWorkoutWeight() + "kg " +
                    mWorkout.getWorkoutSets() + " X " + mWorkout.getWorkoutReps() + "\n";
        }
        return string;
    }
}