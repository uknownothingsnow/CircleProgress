package com.github.lzyzsd.circleprogressexample;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.Timer;
import java.util.TimerTask;

public class MyActivity extends ActionBarActivity {
    private Timer timer;
    private DonutProgress donutProgress;
    private CircleProgress circleProgress;
    private ArcProgress arcProgress;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);

        /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim2);
        set.setTarget(donutProgress);
        set.start();*/

        /*ValueAnimator animation = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        animation.setDuration(1000);
        animation.start();*/

        /*PropertyValuesHolder donutAlphaProperty = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutProgressProperty = PropertyValuesHolder.ofInt("donut_progress", 0, 100);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(donutProgress, donutAlphaProperty, donutProgressProperty);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();*/

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean a = false;
                        if (a) {
                            ObjectAnimator anim = ObjectAnimator.ofInt(donutProgress, "progress", 0, 10);
                            anim.setInterpolator(new DecelerateInterpolator());
                            anim.setDuration(500);
                            anim.start();
                        } else {
                            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MyActivity.this, R.animator.progress_anim);
                            set.setInterpolator(new DecelerateInterpolator());
                            set.setTarget(donutProgress);
                            set.start();
                        }
                    }
                });
            }
        }, 0, 2000);
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
