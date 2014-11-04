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
public class CircleView extends View {
    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private Paint innerCirclePaint;
    private Paint textPaint;

    private float textSize;
    private int progress = 0;
    private int max = 100;
    private boolean showText = true;
    public enum ProgressTextVisibility{
        Visible,Invisible
    };
    private static final int PROGRESS_TEXT_VISIBLE = 0;
    private static final int PROGRESS_TEXT_INVISIBLE = 1;

    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float finishedStrokeWidth;
    private float unfinishedStrokeWidth;
    private int textColor;

    private final int default_finished_color = Color.rgb(66, 145, 241);
    private final int default_unfinished_color = Color.rgb(204, 204, 204);
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final float default_stroke_width;
    private final float default_text_size;
    private final int min_size;

    private String prefixText = "";
    private String suffixText = "%";

    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_SHOW_TEXT = "show_text";

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_stroke_width = dp2px(10);
        default_text_size = sp2px(18);
        min_size = (int) dp2px(100);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, 0);

        finishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_progress_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_progress_unfinished_color, default_unfinished_color);
        textColor = attributes.getColor(R.styleable.CircleProgress_progress_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.CircleProgress_progress_text_size, default_text_size);

        finishedStrokeWidth = attributes.getDimension(R.styleable.CircleProgress_progress_finished_stroke_width, default_stroke_width);
        unfinishedStrokeWidth = attributes.getDimension(R.styleable.CircleProgress_progress_unfinished_stroke_width, default_stroke_width);

        int textVisible = attributes.getInt(R.styleable.CircleProgress_progress_text_visibility, PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            showText = false;
        }

        setProgress(attributes.getInt(R.styleable.CircleProgress_progress, 0));
        setMax(attributes.getInt(R.styleable.CircleProgress_max, 100));
        attributes.recycle();

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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
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

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }

    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }

    public float getFinishedStrokeWidth() {
        return finishedStrokeWidth;
    }

    public float getUnfinishedStrokeWidth() {
        return unfinishedStrokeWidth;
    }

    public String getPrefixText() {
        return prefixText;
    }

    public String getSuffixText() {
        return suffixText;
    }

    private float getProgressAngle() {
        return getProgress() / (float) max * 360f;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
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

    RectF finishedOuterRect = new RectF();
    RectF unfinishedOuterRect = new RectF();
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
        float innerCircleRadius = getWidth() - Math.min(finishedStrokeWidth, unfinishedStrokeWidth) / 2.0f + Math.abs(finishedStrokeWidth - unfinishedStrokeWidth) / 2f;
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

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public float sp2px(float sp){
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
