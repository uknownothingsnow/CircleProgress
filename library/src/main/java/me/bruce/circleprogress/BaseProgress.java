package me.bruce.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 11/4/14.
 */
public class BaseProgress extends View {
    protected static final int PROGRESS_TEXT_VISIBLE = 0;
    protected static final int PROGRESS_TEXT_INVISIBLE = 1;

    protected Paint textPaint;

    protected float textSize;
    protected int textColor;
    protected int progress = 0;
    protected int max = 100;
    protected boolean showText = true;
    protected int finishedStrokeColor;
    protected int unfinishedStrokeColor;
    protected int default_finished_color = Color.rgb(66, 145, 241);
    protected int default_unfinished_color = Color.rgb(204, 204, 204);
    protected int default_text_color = Color.rgb(66, 145, 241);
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

    public BaseProgress(Context context) {
        this(context, null);
    }

    public BaseProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        initConstants();
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();
        initPainters();
    }

    protected void initConstants() {
        default_text_size = sp2px(18);
        min_size = (int) dp2px(100);
    }

    protected void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_progress_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_progress_unfinished_color, default_unfinished_color);
        textColor = attributes.getColor(R.styleable.CircleProgress_progress_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.CircleProgress_progress_text_size, default_text_size);

        int textVisible = attributes.getInt(R.styleable.CircleProgress_progress_text_visibility, PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            showText = false;
        }

        setProgress(attributes.getInt(R.styleable.CircleProgress_progress, 0));
        setMax(attributes.getInt(R.styleable.CircleProgress_max, 100));
    }

    protected void initPainters() {
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

    public String getPrefixText() {
        return prefixText;
    }

    public String getSuffixText() {
        return suffixText;
    }

    public String getDrawText() {
        return getPrefixText() + getProgress() + getSuffixText();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
    }

    public float getProgressPercentage() {
        return getProgress() / (float) getMax();
    }

    protected float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    protected float sp2px(float sp){
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
