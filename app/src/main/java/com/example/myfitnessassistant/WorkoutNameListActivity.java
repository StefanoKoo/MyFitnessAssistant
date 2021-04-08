package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import data.WorkoutNameDao;
import data.WorkoutNameDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class WorkoutNameListActivity extends AppCompatActivity {
    private static String TAG = WorkoutNameListActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private ActionBar mActionbar;

    private WorkoutNameDatabase db_workout_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_name_list);

        // Toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_20);

        db_workout_name = Room.databaseBuilder(this,WorkoutNameDatabase.class,"DB_Workout_name").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_workout_name_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.d(TAG,"Back Button Clicked");
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
            case R.id.save_workouts:
                Log.d(TAG,"Save Button Clicked");
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//                db_workout_name.WorkoutNameDao().
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}