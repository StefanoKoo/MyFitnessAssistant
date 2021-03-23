package com.example.myfitnessassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PopUpActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up);

        findViewById(R.id.button_confirm).setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}