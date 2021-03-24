package com.github.lzyzsd.circleprogressexample;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.Timer;
import java.util.TimerTask;

public class MyActivity extends AppCompatActivity
{
    private Timer timer;
    private DonutProgress donutProgress;
    private CircleProgress circleProgress;
    private ArcProgress arcProgress;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vector);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);

        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        boolean a = false;
                        if (a)
                        {
                            @SuppressLint("ObjectAnimatorBinding")
                            ObjectAnimator anim = ObjectAnimator
                                    .ofInt(donutProgress, "progress", 0, 10);
                            anim.setInterpolator(new DecelerateInterpolator());
                            anim.setDuration(500);
                            anim.start();
                        }
                        else
                        {
                            AnimatorSet set = (AnimatorSet) AnimatorInflater
                                    .loadAnimator(MyActivity.this, R.animator.progress_anim);
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
    protected void onDestroy()
    {
        super.onDestroy();
        timer.cancel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_viewpager:
                startActivity(new Intent(this, ViewPagerActivity.class));
                return true;
            case R.id.action_list:
                startActivity(new Intent(this, ItemListActivity.class));
                return true;
            case R.id.action_arch_tab:
                startActivity(new Intent(this, ArcInFragment.class));
                return true;
            case R.id.action_change_central_text:
                startActivity(new Intent(this, CentralTextExampleActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
