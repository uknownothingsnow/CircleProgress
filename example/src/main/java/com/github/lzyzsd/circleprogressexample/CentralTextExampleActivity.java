package com.github.lzyzsd.circleprogressexample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

public class CentralTextExampleActivity extends ActionBarActivity {

    ArcProgress arcProgress;
    CircleProgress circleProgress;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_text_example);

        arcProgress = findViewById(R.id.arc_progress);
        arcProgress.setText("-50");
        arcProgress.setSuffixText("°C");
        arcProgress.setProgress(25);

        circleProgress = findViewById(R.id.donut_progress);
        circleProgress.setProgress(75);

        editText = findViewById(R.id.editText);
    }

    public void setCustomText(View view){
        if(String.valueOf(editText.getText()) != null || !String.valueOf(editText.getText()).equals("")) {
            arcProgress.setText(String.valueOf(editText.getText()));
            circleProgress.setText(String.valueOf(editText.getText()));
        } else{
            Toast.makeText(this, "Field should not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
