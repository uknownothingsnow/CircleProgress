package me.bruce.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 14-10-30.
 */
public class CircleView extends View {
    private Paint finishedPaint;
    private Paint innerPaint;
    private Paint textPaint;
    private int strokeWidth = 20;
    private int textSize = 38;
    private int progress = 80;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        finishedPaint = new Paint();
        finishedPaint.setColor(Color.GREEN);
        finishedPaint.setStyle(Paint.Style.STROKE);
        finishedPaint.setAntiAlias(true);
        finishedPaint.setStrokeWidth(strokeWidth);

        innerPaint = new Paint();
        innerPaint.setColor(Color.WHITE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private float getProgressAngle() {
        return getProgress() * 3.6f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF outerRect = new RectF(getLeft() + strokeWidth / 2, getTop() + strokeWidth / 2, getRight() - strokeWidth / 2, getBottom() - strokeWidth / 2);
        canvas.drawArc(outerRect, 0, getProgressAngle(), false, finishedPaint);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, (getWidth() - strokeWidth) / 2.0f, innerPaint);
        String text = progress + "%";
        float textHeight = textPaint.descent() + textPaint.ascent();

        canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);
    }
}
