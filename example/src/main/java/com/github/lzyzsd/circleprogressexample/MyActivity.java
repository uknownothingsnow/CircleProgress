package com.github.lzyzsd.circleprogressexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_viewpager:
                startActivity(new Intent(this, ViewPagerActivity.class));
                return true;
            case R.id.action_list:
                startActivity(new Intent(this, ItemListActivity.class));
                return true;
            case R.id.action_arch_tab:
                startActivity(new Intent(this, ArcInFragment.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
