package me.bruce.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 11/6/14.
 */
public class ArcProgress extends View {
    Paint paint = new Paint();
    Paint textPaint = new Paint();
    int strokeWidth = 8;
    int max = 80;
    int progress = 11;

    int textColor = Color.rgb(220, 228, 240);

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.rgb(72, 106, 176));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        textPaint.setTextSize(80);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = 360 * (max / 100.f);
        float startAngle = 270 - sweepAngle / 2f;
        float finishedSweepAngle = progress / 100f * sweepAngle;
        float finishedStartAngle = startAngle;
        canvas.drawArc(new RectF(strokeWidth / 2f, strokeWidth / 2f, getWidth() - strokeWidth / 2f, getHeight() - strokeWidth / 2f), startAngle, sweepAngle, false, paint);
        paint.setColor(Color.WHITE);
        canvas.drawArc(new RectF(strokeWidth / 2f, strokeWidth / 2f, getWidth() - strokeWidth / 2f, getHeight() - strokeWidth / 2f), finishedStartAngle, finishedSweepAngle, false, paint);

        String text = "11";
        String suffix = "%";
        float textHeight = textPaint.descent() + textPaint.ascent();
        float textBaseline = (getHeight() - textHeight) / 2.0f;
        canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, textBaseline, textPaint);
        textPaint.setTextSize(30);
        float suffixHeight = textPaint.descent() + textPaint.ascent();
        canvas.drawText(suffix, (getWidth() + textPaint.measureText(text)) / 2.0f + 20, textBaseline + textHeight - suffixHeight, textPaint);
        textPaint.setTextSize(20);
        float bottomTextBaseline = getHeight() - (float) Math.tan(15/180f*Math.PI) * getWidth() / 2 - (textPaint.descent() + textPaint.ascent());
        canvas.drawText("STORAGE", (getWidth() - textPaint.measureText("STORAGE")) / 2.0f, bottomTextBaseline, textPaint);
    }
}
