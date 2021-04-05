package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import data.MyEventDay;
import data.Workout;
import data.WorkoutDatabase;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WorkoutListActivity extends AppCompatActivity implements WorkoutsRecyclerAdapter.OnRoutineListener, WorkoutsRecyclerAdapter.OnSwipeListener, View.OnClickListener{
    private static String TAG = "MakeRoutineActivity";

    static Context context;

    private static int REQUEST_POP_UP = 0;
    private static int REQUEST_EDIT = 1;

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private FloatingActionButton mFloatingActionButton;

    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerAdapter mWorkoutRecyclerAdapter;
    private ArrayList<Workout> mWorkouts;

    private Drawable mImageDrawable;
    private String mRoutineText;

    WorkoutDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        context = getApplicationContext();

        // Toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_20);

        // RecyclerView showing Workout list
        mRecyclerView = findViewById(R.id.list_workouts);
        mWorkouts = new ArrayList<>();
        mImageDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dumbell_20, null);

        findViewById(R.id.add_button).setOnClickListener(this::onClick);

        // Set toolbar's title with date
        Intent intent = getIntent();
        MyEventDay myEventDay = intent.getParcelableExtra("Test Item");
        String note = myEventDay.getNote();
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(myEventDay.getCalendar().getTime());
        mActionbar.setTitle(date);

        initComponents();
//        insertFakeRoutines();

        // Database Init
        db = Room.databaseBuilder(this,WorkoutDatabase.class,"DB_Workout").allowMainThreadQueries().build();
        if (db.workoutDao().getAll().size() != 0) {
            for (int i = 1; i <= db.workoutDao().getAll().size() + 1; i++) {
                if (db.workoutDao().getWorkoutByIndex(i) != null) {
                    Log.d(TAG,db.workoutDao().getAll().toString());
                    addWorkout(db.workoutDao().getWorkoutByIndex(i));
                }
            }
        }
    }

    private void initComponents() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mWorkoutRecyclerAdapter = new WorkoutsRecyclerAdapter(mWorkouts, this, this);
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mWorkoutRecyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mWorkoutRecyclerAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mWorkoutRecyclerAdapter);
    }

    private void insertFakeRoutines() {
        mRoutineText = "New Routine";

        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
    }

    private void addItem(Drawable icon, String mainText) {
        Workout item = new Workout();
        item.setWorkoutName(mainText);
        mWorkouts.add(item);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void addWorkout(Workout mWorkout) {
        mWorkouts.add(mWorkout);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_make_routine, menu);
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
            case R.id.save_routine:
                Log.d(TAG,"Save Button Clicked");
                saveWorkoutList(mWorkouts);
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    // Floating Action Button Click
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,PopUpActivity.class);
        startActivityForResult(intent,REQUEST_POP_UP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_POP_UP) {
            // 팝업 창에서 '확인' 버튼을 눌렀을 때 진입
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"Result OK");
                Workout mWorkout = data.getParcelableExtra("Workout");
                addWorkout(mWorkout);
                db.workoutDao().insert(mWorkout);
            }
            // 팝업 창에서 '취소' 버튼을 눌렀을 때 진입
            else {
                Log.d(TAG,"Result Not OK");
            }
        }
        else {
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"New Result OK");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRoutineClick(int position) {
        Toast.makeText(this,"Routine Click Test",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWorkoutSwipe(int position, int direction) {
        Log.d(TAG,"onWorkoutSwipe Called");
        Intent intent = new Intent(this,PopUpActivity.class);
        intent.putExtra("Edit Workout",mWorkouts.get(position));
        startActivityForResult(intent,REQUEST_EDIT);
    }

    // 현재 Workout 리스트를 DB에 추가
    public void saveWorkoutList(ArrayList<Workout> mWorkouts) {
        for (int i = 0; i < mWorkouts.size(); i++) {
            Workout mWorkout = mWorkouts.get(i);
//            db.workoutDao().insert(mWorkout);
            Log.d(TAG,"Workout " + i + " " + mWorkout.getWorkoutName());
        }
    }
}