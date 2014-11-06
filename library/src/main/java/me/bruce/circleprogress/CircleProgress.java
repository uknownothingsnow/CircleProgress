package me.bruce.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 11/4/14.
 */
public class CircleProgress extends View {
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

    private Paint paint = new Paint();
    private Path path = new Path();
    
    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initConstants();

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initConstants() {
        default_text_size = Utils.sp2px(getResources(), 18);
        default_text_color = Color.WHITE;
        min_size = (int) Utils.dp2px(getResources(), 100);
    }

    protected void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_circle_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.CircleProgress_circle_unfinished_color, default_unfinished_color);
        textColor = attributes.getColor(R.styleable.CircleProgress_circle_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.CircleProgress_circle_text_size, default_text_size);

        int textVisible = attributes.getInt(R.styleable.CircleProgress_circle_text_visibility, PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            showText = false;
        }

        setMax(attributes.getInt(R.styleable.CircleProgress_circle_max, default_max));
        setProgress(attributes.getInt(R.styleable.CircleProgress_circle_progress, 0));
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
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

    @Override protected void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (path.isEmpty()) {
            path.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, Path.Direction.CCW);
        }

        canvas.clipPath(path, Region.Op.INTERSECT);
        paint.setColor(getUnfinishedStrokeColor());
        canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, paint);

        canvas.clipPath(path, Region.Op.INTERSECT);
        paint.setColor(getFinishedStrokeColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, getHeight() * (1 - getProgressPercentage()), getWidth(), getHeight(), paint);

        String text = getDrawText();
        float textHeight = textPaint.descent() + textPaint.ascent();
        canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);
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
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
