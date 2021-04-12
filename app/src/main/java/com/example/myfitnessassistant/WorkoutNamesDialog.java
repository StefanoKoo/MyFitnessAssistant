package com.example.myfitnessassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class WorkoutNamesDialog extends Activity implements View.OnClickListener{
    private EditText mNameWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_workout_names_dialog);

        initLayout();
    }

    private void initLayout() {
        mNameWorkout = findViewById(R.id.string_name_workout);
        findViewById(R.id.button_add).setOnClickListener(this);
        findViewById(R.id.button_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = mNameWorkout.getText().toString();

        boolean isEmpty = name.length() == 0;

        switch (view.getId()) {
            case (R.id.button_add):
                if (isEmpty) {
                    finish();
                    break;
                }
                Intent intent = new Intent();
                intent.putExtra("Name",name);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case (R.id.button_cancel):
                if (isEmpty) {
                    finish();
                    break;
                }
                Intent intent1 = new Intent();
                intent1.putExtra("Name",name);
                setResult(RESULT_CANCELED,intent1);
                finish();
                break;
        }
    }
}