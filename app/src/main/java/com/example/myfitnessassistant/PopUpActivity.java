package com.example.myfitnessassistant;

import data.Workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class PopUpActivity extends Activity implements View.OnClickListener {
    private EditText mNameWorkout, mWeightWorkout, mSetsWorkout, mRepsWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up);

        initLayout();
    }

    private void initLayout() {
        mNameWorkout = findViewById(R.id.string_name_workout);
        mWeightWorkout = findViewById(R.id.double_weight_workout);
        mSetsWorkout = findViewById(R.id.integer_sets_workout);
        mRepsWorkout = findViewById(R.id.integer_reps_workout);

        findViewById(R.id.button_confirm).setOnClickListener(this);
        findViewById(R.id.button_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case(R.id.button_confirm):
                // MakeRoutine Activity 로 결과 전송
                Log.d("PopUpActivity","Confirm Button Clicked");
                Workout mWorkout = new Workout(
                        mNameWorkout.getText().toString(),
                        Double.parseDouble(mWeightWorkout.getText().toString()),
                        Integer.parseInt(mSetsWorkout.getText().toString()),
                        Integer.parseInt(mRepsWorkout.getText().toString()));
                Intent intent = new Intent();
                intent.putExtra("Workout", mWorkout);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case(R.id.button_cancel):
                finish();
                break;
        }
    }
}