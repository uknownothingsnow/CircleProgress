package me.bruce.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 14-10-30.
 */
public class DonutProgress extends BaseProgress {
    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private Paint innerCirclePaint;

    private float finishedStrokeWidth;
    private float unfinishedStrokeWidth;

    private final float default_stroke_width;

    RectF finishedOuterRect = new RectF();
    RectF unfinishedOuterRect = new RectF();

    private static final String INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width";

    public DonutProgress(Context context) {
        this(context, null);
    }

    public DonutProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DonutProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_stroke_width = dp2px(10);
        initPaints();
    }

    private void initPaints() {
        finishedPaint = new Paint();
        finishedPaint.setColor(finishedStrokeColor);
        finishedPaint.setStyle(Paint.Style.STROKE);
        finishedPaint.setAntiAlias(true);
        finishedPaint.setStrokeWidth(finishedStrokeWidth);

        unfinishedPaint = new Paint();
        unfinishedPaint.setColor(unfinishedStrokeColor);
        unfinishedPaint.setStyle(Paint.Style.STROKE);
        unfinishedPaint.setAntiAlias(true);
        unfinishedPaint.setStrokeWidth(unfinishedStrokeWidth);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.WHITE);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void initByAttributes(TypedArray attributes) {
        super.initByAttributes(attributes);
        finishedStrokeWidth = attributes.getDimension(R.styleable.CircleProgress_progress_finished_stroke_width, default_stroke_width);
        unfinishedStrokeWidth = attributes.getDimension(R.styleable.CircleProgress_progress_unfinished_stroke_width, default_stroke_width);
    }

    public float getFinishedStrokeWidth() {
        return finishedStrokeWidth;
    }

    public float getUnfinishedStrokeWidth() {
        return unfinishedStrokeWidth;
    }

    private float getProgressAngle() {
        return getProgress() / (float) max * 360f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int measureSpec){
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = min_size;
            if(mode == MeasureSpec.AT_MOST){
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (finishedStrokeWidth > unfinishedStrokeWidth) {
            finishedOuterRect.set(finishedStrokeWidth / 2,
                finishedStrokeWidth / 2,
                getWidth() - finishedStrokeWidth / 2,
                getHeight() - finishedStrokeWidth / 2);
            float delta = (finishedStrokeWidth - unfinishedStrokeWidth) / 2f;
            unfinishedOuterRect.set(unfinishedStrokeWidth / 2,
                unfinishedStrokeWidth / 2,
                getWidth() - unfinishedStrokeWidth / 2 - delta,
                getHeight() - unfinishedStrokeWidth / 2 - delta);
        } else {
            float delta = (unfinishedStrokeWidth - finishedStrokeWidth) / 2f;
            finishedOuterRect.set(finishedStrokeWidth / 2,
                finishedStrokeWidth / 2,
                getWidth() - finishedStrokeWidth / 2 - delta,
                getHeight() - finishedStrokeWidth / 2 - delta);
            unfinishedOuterRect.set(unfinishedStrokeWidth / 2,
                unfinishedStrokeWidth / 2,
                getWidth() - unfinishedStrokeWidth / 2,
                getHeight() - unfinishedStrokeWidth / 2);
        }
        float innerCircleRadius = (getWidth() - Math.min(finishedStrokeWidth, unfinishedStrokeWidth) + Math.abs(finishedStrokeWidth - unfinishedStrokeWidth)) / 2f;
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, innerCircleRadius, innerCirclePaint);
        canvas.drawArc(finishedOuterRect, 0, getProgressAngle(), false, finishedPaint);
        canvas.drawArc(unfinishedOuterRect, getProgressAngle(), 360 - getProgressAngle(), false, unfinishedPaint);

        if (showText) {
            String text = prefixText + progress + suffixText;
            float textHeight = textPaint.descent() + textPaint.ascent();
            canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, getFinishedStrokeWidth());
        bundle.putFloat(INSTANCE_UNFINISHED_STROKE_WIDTH, getUnfinishedStrokeWidth());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffixText());
        bundle.putString(INSTANCE_PREFIX, getPrefixText());
        bundle.putBoolean(INSTANCE_SHOW_TEXT, showText);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH);
            unfinishedStrokeWidth = bundle.getFloat(INSTANCE_UNFINISHED_STROKE_WIDTH);
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
            initPaints();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            prefixText = bundle.getString(INSTANCE_PREFIX);
            suffixText = bundle.getString(INSTANCE_SUFFIX);
            showText = bundle.getBoolean(INSTANCE_SHOW_TEXT);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
