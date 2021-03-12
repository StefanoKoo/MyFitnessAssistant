package com.example.myfitnessassistant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ActionBar mActionbar;

    RecyclerView routineList;
    RecyclerViewAdapter mAdapter = null;
    ArrayList<RecyclerViewItemRoutine> mList;

    private Drawable mImageDrawable;
    private String mRoutineText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Toolbar Setup */
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_account_circle_20);


        /* Routine List Setup */
        routineList = findViewById(R.id.list_routines);
        mList = new ArrayList<>();

        mAdapter = new RecyclerViewAdapter(mList);
        routineList.setAdapter(mAdapter);
        routineList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mImageDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_dumbell_20, null);
        mRoutineText = "New Routine\n\n\n\n\n\n";

        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);
        addItem(mImageDrawable, mRoutineText);

        ImageButton mWorkOutAddButton = findViewById(R.id.btn_addRoutine);
        mWorkOutAddButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MakeRoutineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addItem(Drawable icon, String mainText) {
        RecyclerViewItemRoutine item = new RecyclerViewItemRoutine();
        item.setIcon(icon);
        item.setRoutineTitle(mainText);
        mList.add(item);
    }
}