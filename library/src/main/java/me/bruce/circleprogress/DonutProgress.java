package me.bruce.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 14-10-30.
 */
public class DonutProgress extends View {
    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private Paint innerCirclePaint;

    private float finishedStrokeWidth;
    private float unfinishedStrokeWidth;

    private float default_stroke_width;

    RectF finishedOuterRect = new RectF();
    RectF unfinishedOuterRect = new RectF();

    private static final String INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width";

    protected static final int PROGRESS_TEXT_VISIBLE = 0;
    protected static final int PROGRESS_TEXT_INVISIBLE = 1;

    protected Paint textPaint;

    protected float textSize;
    protected int textColor;
    protected int progress = 0;
    protected int max;
    protected boolean showText = true;
    protected int finishedStrokeColor;
    protected int unfinishedStrokeColor;
    protected int default_finished_color = Color.rgb(66, 145, 241);
    protected int default_unfinished_color = Color.rgb(204, 204, 204);
    protected int default_text_color = Color.rgb(66, 145, 241);
    protected int default_max = 100;
    protected float default_text_size;
    protected int min_size;

    protected String prefixText = "";
    protected String suffixText = "%";

    protected static final String INSTANCE_STATE = "saved_instance";
    protected static final String INSTANCE_TEXT_COLOR = "text_color";
    protected static final String INSTANCE_TEXT_SIZE = "text_size";
    protected static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    protected static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    protected static final String INSTANCE_MAX = "max";
    protected static final String INSTANCE_PROGRESS = "progress";
    protected static final String INSTANCE_SUFFIX = "suffix";
    protected static final String INSTANCE_PREFIX = "prefix";
    protected static final String INSTANCE_SHOW_TEXT = "show_text";

    public enum ProgressTextVisibility{
        Visible,Invisible
    };

    public DonutProgress(Context context) {
        this(context, null);
    }

    public DonutProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DonutProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initConstants();

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DonutProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initConstants() {
        default_text_size = Utils.sp2px(getResources(), 18);
        min_size = (int) Utils.dp2px(getResources(), 100);
        default_stroke_width = Utils.dp2px(getResources(), 10);
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

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
    }

    protected void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.DonutProgress_donut_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.DonutProgress_donut_unfinished_color, default_unfinished_color);
        textColor = attributes.getColor(R.styleable.DonutProgress_donut_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.DonutProgress_donut_text_size, default_text_size);

        int textVisible = attributes.getInt(R.styleable.DonutProgress_donut_text_visibility, PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            showText = false;
        }

        setMax(attributes.getInt(R.styleable.DonutProgress_donut_max, default_max));
        setProgress(attributes.getInt(R.styleable.DonutProgress_donut_progress, 0));
        finishedStrokeWidth = attributes.getDimension(R.styleable.DonutProgress_donut_finished_stroke_width, default_stroke_width);
        unfinishedStrokeWidth = attributes.getDimension(R.styleable.DonutProgress_donut_unfinished_stroke_width, default_stroke_width);
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

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int finishedStrokeColor) {
        this.finishedStrokeColor = finishedStrokeColor;
    }

    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor) {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
    }

    public String getSuffixText() {
        return suffixText;
    }

    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
    }

    public String getPrefixText() {
        return prefixText;
    }

    public void setPrefixText(String prefixText) {
        this.prefixText = prefixText;
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
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffixText());
        bundle.putString(INSTANCE_PREFIX, getPrefixText());
        bundle.putBoolean(INSTANCE_SHOW_TEXT, showText);
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, getFinishedStrokeWidth());
        bundle.putFloat(INSTANCE_UNFINISHED_STROKE_WIDTH, getUnfinishedStrokeWidth());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
            initPainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            prefixText = bundle.getString(INSTANCE_PREFIX);
            suffixText = bundle.getString(INSTANCE_SUFFIX);
            showText = bundle.getBoolean(INSTANCE_SHOW_TEXT);
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH);
            unfinishedStrokeWidth = bundle.getFloat(INSTANCE_UNFINISHED_STROKE_WIDTH);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
