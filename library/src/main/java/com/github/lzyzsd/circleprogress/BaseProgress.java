package com.github.lzyzsd.circleprogress;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.text.DecimalFormat;

/**
 * Created by KhaledLela on 9/27/17.
 */

public abstract class BaseProgress extends View{
    /**
     * decimalformat for formatting
     */
    protected DecimalFormat mFormat;
    protected float progress;
    protected int max;
    private ObjectAnimator objectAnimator;

    /**
     * the number of decimal digits this formatter uses
     */
    protected int digits = 0;

    public BaseProgress(Context context) {
        super(context);
    }

    public BaseProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    abstract void initByAttributes(TypedArray attributes) ;
    public void setProgressFloating(int digits){
        this.digits = digits;
        setFormatter();
    }

    protected void setFormatter() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                b.append(".");
            b.append("0");
        }
        mFormat = new DecimalFormat("###,###,###,##0" + b.toString());
    }

    /**
     * Set progress with animation.
     * @param progress end progress
     * @param duration time for animation.
     */
    public void setProgressWithAnimation(float progress, int duration) {
        setProgressWithAnimation(getProgress(),progress,duration,null);
    }

    public void setProgressWithAnimation(float progress, int duration, Animator.AnimatorListener listener) {
        setProgressWithAnimation(getProgress(),progress,duration,listener);
    }
    public void setProgressWithAnimation(float startProgress,float endProgress, int duration, Animator.AnimatorListener listener) {
        cancelAnimation(); // Clear previous animation if exist.
        objectAnimator = ObjectAnimator.ofFloat(this, "progress",startProgress,endProgress);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(listener);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }


    public void cancelAnimation(){
        if (objectAnimator !=null && objectAnimator.isRunning())
            objectAnimator.cancel();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public float getProgress() {
        return progress;
    }

    public float getFormattedProgress() {
        try {
            return Float.parseFloat(mFormat.format(progress));
        }catch (Exception ex){
            return progress;
        }
    }
}
