package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.myfitnessassistant.data.DateWorkout;
import com.example.myfitnessassistant.data.DateWorkoutDatabase;
import com.example.myfitnessassistant.data.Workout;
import com.example.myfitnessassistant.data.WorkoutDatabase;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkoutListActivity extends AppCompatActivity implements WorkoutsRecyclerAdapter.OnRoutineListener, WorkoutsRecyclerAdapter.OnSwipeListener, View.OnClickListener{
    private static String TAG = "MakeRoutineActivity";

    static Context context;

    private static int REQUEST_POP_UP = 0;
    private static int REQUEST_EDIT = 1;

    private Toolbar mToolbar;
    private ActionBar mActionbar;

    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerAdapter mWorkoutRecyclerAdapter;
    private ArrayList<Workout> mWorkouts;

    private Drawable mImageDrawable;
    private String mRoutineText;

    private DateWorkout dateWorkout;
    public static String date;

    WorkoutDatabase db;
    DateWorkoutDatabase db2;

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
        mImageDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dumbell_20, null);

        findViewById(R.id.add_button).setOnClickListener(this::onClick);

        // Set toolbar's title with date
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");
        mActionbar.setTitle(date);


//        insertFakeRoutines();

        db2 = Room.databaseBuilder(this,DateWorkoutDatabase.class,"DB_Workout").allowMainThreadQueries().build();
        dateWorkout = db2.DateWorkoutDao().getDateWorkoutByDate(date);
        if (dateWorkout == null) {
            Log.d(TAG,"No Workout Today");
            dateWorkout = new DateWorkout(date);
            db2.DateWorkoutDao().insert(dateWorkout);
        }
        mWorkouts = dateWorkout.getWorkouts();
        Log.d(TAG,mWorkouts.toString());

        initComponents();
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
        Log.d(TAG,"ADD");
        mWorkouts.add(mWorkout);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_workout_list, menu);
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
                saveWorkoutList(mWorkouts);
                Log.d(TAG, setWorkoutsString(mWorkouts));
                Intent intent = new Intent();
                intent.putExtra("workouts_summary",setWorkoutsString(mWorkouts));
                setResult(RESULT_OK,intent);
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
        Intent intent = new Intent(this, WorkoutsDialog.class);
        startActivityForResult(intent,REQUEST_POP_UP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_POP_UP) {
            // ?????? ????????? '??????' ????????? ????????? ??? ??????
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"Result OK");
                Workout mWorkout = data.getParcelableExtra("Workout");
                addWorkout(mWorkout);
            }
            // ?????? ????????? '??????' ????????? ????????? ??? ??????
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
        Intent intent = new Intent(this, WorkoutsDialog.class);
        intent.putExtra("Edit Workout",mWorkouts.get(position));
        startActivityForResult(intent,REQUEST_EDIT);
    }

    // ?????? Workout ???????????? DB??? ??????
    public void saveWorkoutList(ArrayList<Workout> mWorkouts) {
        db2.DateWorkoutDao().updateWorkouts(date,mWorkouts);
    }

    public String setWorkoutsString(ArrayList<Workout> workouts) {
        String summary = "";

        for (Workout mWorkout:workouts) {
            summary += mWorkout.getWorkoutName() + " : " +
                    mWorkout.getWorkoutWeight() + "kg " +
                    mWorkout.getWorkoutSets() + " X " + mWorkout.getWorkoutReps() + "\n";
        }
        return summary;
    }
}