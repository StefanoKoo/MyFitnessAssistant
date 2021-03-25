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
import data.MyEventDay;
import data.Workout;

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

public class WorkoutListActivity extends AppCompatActivity implements WorkoutsRecyclerAdapter.OnRoutineListener, WorkoutsRecyclerAdapter.OnExpandListener, View.OnClickListener{
    private static String TAG = "MakeRoutineActivity";

    private static Integer REQUEST_POP_UP = 0;

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private FloatingActionButton mFloatingActionButton;

    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerAdapter mWorkoutRecyclerAdapter;
    private ArrayList<Workout> mWorkouts;

    private Drawable mImageDrawable;
    private String mRoutineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_20);

        mRecyclerView = findViewById(R.id.list_workouts);
        mWorkouts = new ArrayList<>();
        mImageDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dumbell_20, null);

        Intent intent = getIntent();
        MyEventDay myEventDay = intent.getParcelableExtra("Test Item");
        String note = myEventDay.getNote();
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(myEventDay.getCalendar().getTime());
        mActionbar.setTitle(date);

        findViewById(R.id.add_button).setOnClickListener(this::onClick);

        initComponents();
        insertFakeRoutines();
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
//        addItem(mImageDrawable, mRoutineText);
//        addItem(mImageDrawable, mRoutineText);
//        addItem(mImageDrawable, mRoutineText);
//        addItem(mImageDrawable, mRoutineText);
//        addItem(mImageDrawable, mRoutineText);
//        addItem(mImageDrawable, mRoutineText);
    }

    private void addItem(Drawable icon, String mainText) {
        Workout item = new Workout();
        item.setWorkoutName(mainText);
        mWorkouts.add(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_make_routine, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Log.d(TAG,"Back Button Clicked");
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            case R.id.save_routine:
                Log.d(TAG,"Save Button Clicked");
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
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
            if (resultCode == RESULT_OK) {
                Log.d(TAG,"Result OK");
                Workout mWorkout = data.getParcelableExtra("Workout");
//                Log.d(TAG,mWorkout.getWorkoutName());
                Toast.makeText(this,mWorkout.getWorkoutName(),Toast.LENGTH_SHORT).show();
                addItem(mImageDrawable,mWorkout.getWorkoutName());
            }
            else {
                Log.d(TAG,"Result Not OK");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRoutineClick(int position) {
        Toast.makeText(this,"Routine Click Test",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExpandClick(int position) {
        Toast.makeText(this,"Expand Click Test",Toast.LENGTH_SHORT).show();
//        if (mExtendedTextView.getVisibility() == View.GONE) {
//            TransitionManager.beginDelayedTransition(mCardView,new AutoTransition());
//            mExtendedTextView.setVisibility(View.VISIBLE);
//        }
//        else {
//            TransitionManager.beginDelayedTransition(mCardView,new AutoTransition());
//            mExtendedTextView.setVisibility(View.GONE);
//        }
    }
}