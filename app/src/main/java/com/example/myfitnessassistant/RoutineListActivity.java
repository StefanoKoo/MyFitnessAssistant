package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import data.Routine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class RoutineListActivity extends AppCompatActivity implements RoutinesRecyclerAdapter.OnRoutineListener, RoutinesRecyclerAdapter.OnExpandListener, View.OnClickListener {
    Toolbar mToolbar;
    ActionBar mActionbar;

    private RecyclerView mRecyclerView;
    private RoutinesRecyclerAdapter mRoutineRecyclerAdapter = null;
    ArrayList<Routine> mRoutines;

    private Drawable mImageDrawable;
    private String mRoutineText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this,"Home Button Clicked",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_list);

        /* Toolbar Setup */
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_account_circle_20);


        /* Routine List Setup */
        mRecyclerView = findViewById(R.id.list_routines);
        mRoutines = new ArrayList<>();

        /* For Debug */
        initRecyclerView();
        insertFakeRoutines();


        findViewById(R.id.btn_addRoutine).setOnClickListener(this::onClick);
    }

    private void insertFakeRoutines() {
        mImageDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dumbell_20, null);
        mRoutineText = "New Routine\n\n\n\n\n\n";

        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRoutineRecyclerAdapter = new RoutinesRecyclerAdapter(mRoutines, this, this);
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mRoutineRecyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mRoutineRecyclerAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mRoutineRecyclerAdapter);
    }

    private void addItem(Drawable icon, String mainText) {
        Routine item = new Routine();
        item.setRoutineTitle(mainText);
        mRoutines.add(item);
    }

    // Routine Itself Click
    @Override
    public void onRoutineClick(int position) {
        Toast.makeText(this,"Routine Click Test",Toast.LENGTH_SHORT).show();
    }

    // Routine Expand Button(Three Dots) Click
    @Override
    public void onExpandClick(int position) {
        Toast.makeText(this,"Expand Click Test",Toast.LENGTH_SHORT).show();
    }

    // Floating Action Button Click
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,MakeRoutineActivity.class);
        startActivity(intent);
    }
}