package com.example.myfitnessassistant;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MakeRoutineActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG = "MakeRoutineActivity";

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_routine);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayShowCustomEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setHomeButtonEnabled(true);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_20);


        Intent intent = getIntent();
        MyEventDay myEventDay = intent.getParcelableExtra("Test Item");
        String note = myEventDay.getNote();
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(myEventDay.getCalendar().getTime());
        mActionbar.setTitle(date);
        Log.d("Test",note);

        findViewById(R.id.add_button).setOnClickListener(this::onClick);
        //TODO : 운동 기입하는 형식 만들기(팝업 형태로 제작, 포함 항목 : 운동이름, 무게, 세트수, 반복수)

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
        startActivity(intent);
    }
}