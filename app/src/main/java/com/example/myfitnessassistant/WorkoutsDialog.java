package com.example.myfitnessassistant;

import com.example.myfitnessassistant.data.Workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class WorkoutsDialog extends Activity implements View.OnClickListener {
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

        Intent intent = getIntent();
        Workout workout = intent.getParcelableExtra("Edit Workout");
        if (workout != null) {
            Log.d("Popup",workout.getWorkoutName());
            mNameWorkout.setText(workout.getWorkoutName());
            mWeightWorkout.setText(String.valueOf(workout.getWorkoutWeight()));
            mSetsWorkout.setText(String.valueOf(workout.getWorkoutSets()));
            mRepsWorkout.setText(String.valueOf(workout.getWorkoutReps()));
        }

    }

    @Override
    public void onClick(View view) {
        String name = mNameWorkout.getText().toString();
        String weight = mWeightWorkout.getText().toString();
        String sets = mSetsWorkout.getText().toString();
        String reps = mRepsWorkout.getText().toString();
        Workout mWorkout = new Workout();

        boolean b = (name.length() == 0 || weight.length() == 0 || sets.length() == 0 || reps.length() == 0);

        switch (view.getId()) {
            case(R.id.button_confirm):
                // MakeRoutine Activity 로 결과 전송
                Log.d("PopUpActivity","Confirm Button Clicked");
                if (b) {
                    finish();
                    break;
                }
                mWorkout.setWorkoutName(name);
                mWorkout.setWorkoutWeight(Double.parseDouble(weight));
                mWorkout.setWorkoutSets(Integer.parseInt(sets));
                mWorkout.setWorkoutReps(Integer.parseInt(reps));
                Intent intent = new Intent();
                intent.putExtra("Workout", mWorkout);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case(R.id.button_cancel):
                Log.d("PopUpActivity","Cancel Button Clicked");
                if (b) {
                    finish();
                    break;
                }
                Intent intent1 = new Intent();
                intent1.putExtra("Workout", mWorkout);
                setResult(RESULT_CANCELED,intent1);
                finish();
                break;
        }
    }
}