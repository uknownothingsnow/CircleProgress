package me.bruce.circleprogress.example;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import me.bruce.circleprogress.ArcProgress;
import me.bruce.circleprogress.CircleProgress;
import me.bruce.circleprogress.DonutProgress;


public class MyActivity extends ActionBarActivity {
    private Timer timer;
    private DonutProgress donutProgress;
    private CircleProgress circleProgress;
    private ArcProgress arcProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        donutProgress.setProgress(donutProgress.getProgress() + 1);
                        circleProgress.setProgress(circleProgress.getProgress() + 1);
                        arcProgress.setProgress(arcProgress.getProgress() + 1);
                    }
                });
            }
        }, 1000, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
