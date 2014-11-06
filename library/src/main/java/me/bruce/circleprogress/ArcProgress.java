package me.bruce.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 11/6/14.
 */
public class ArcProgress extends View {
    private Paint paint;
    private float strokeWidth;
    private float suffixTextSize;
    private float bottomTextSize;
    private String bottomText;

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
    protected int default_finished_color = Color.WHITE;
    protected int default_unfinished_color = Color.rgb(72, 106, 176);
    protected int default_text_color = Color.rgb(66, 145, 241);
    private float default_suffix_text_size;
    private float default_bottom_text_size;
    private float default_stroke_width;
    private String default_suffix_text;
    protected int default_max = 100;
    protected float default_text_size;
    protected int min_size;

    protected String suffixText = "%";

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initConstants();

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initConstants() {
        default_text_size = Utils.sp2px(getResources(), 18);
        min_size = (int) Utils.dp2px(getResources(), 100);
        default_text_color = Color.rgb(220, 228, 240);
        default_text_size = Utils.sp2px(getResources(), 40);
        default_max = 80;
        default_suffix_text_size = Utils.sp2px(getResources(), 15);
        default_suffix_text = "%";
        default_bottom_text_size = Utils.sp2px(getResources(), 10);
        default_stroke_width = Utils.dp2px(getResources(), 4);
    }

    protected void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_unfinished_color, default_unfinished_color);
        textColor = attributes.getColor(R.styleable.ArcProgress_arc_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.ArcProgress_arc_text_size, default_text_size);

        int textVisible = attributes.getInt(R.styleable.ArcProgress_arc_text_visibility, PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            showText = false;
        }

        setMax(attributes.getInt(R.styleable.ArcProgress_arc_max, default_max));
        setProgress(attributes.getInt(R.styleable.ArcProgress_arc_progress, 0));
        strokeWidth = attributes.getDimension(R.styleable.ArcProgress_arc_stroke_width, default_stroke_width);
        suffixTextSize = attributes.getDimension(R.styleable.ArcProgress_arc_suffix_text_size, default_suffix_text_size);
        suffixText = TextUtils.isEmpty(attributes.getString(R.styleable.ArcProgress_arc_suffix_text)) ? default_suffix_text : attributes.getString(R.styleable.ArcProgress_arc_suffix_text);
        bottomTextSize = attributes.getDimension(R.styleable.ArcProgress_arc_bottom_text_size, default_bottom_text_size);
        bottomText = attributes.getString(R.styleable.ArcProgress_arc_bottom_text);
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(default_unfinished_color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public float getSuffixTextSize() {
        return suffixTextSize;
    }

    public void setSuffixTextSize(float suffixTextSize) {
        this.suffixTextSize = suffixTextSize;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = 360 * (max / 100.f);
        float startAngle = 270 - sweepAngle / 2f;
        float finishedSweepAngle = progress / 100f * sweepAngle;
        float finishedStartAngle = startAngle;
        paint.setColor(default_unfinished_color);
        canvas.drawArc(new RectF(strokeWidth / 2f, strokeWidth / 2f, getWidth() - strokeWidth / 2f, getHeight() - strokeWidth / 2f), startAngle, sweepAngle, false, paint);
        paint.setColor(default_finished_color);
        canvas.drawArc(new RectF(strokeWidth / 2f, strokeWidth / 2f, getWidth() - strokeWidth / 2f, getHeight() - strokeWidth / 2f), finishedStartAngle, finishedSweepAngle, false, paint);

        String text = String.valueOf(getProgress());
        String suffix = "%";
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        float textHeight = textPaint.descent() + textPaint.ascent();
        float textBaseline = (getHeight() - textHeight) / 2.0f;
        canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, textBaseline, textPaint);

        textPaint.setTextSize(suffixTextSize);
        float suffixHeight = textPaint.descent() + textPaint.ascent();
        canvas.drawText(suffix, (getWidth() + textPaint.measureText(text)) / 2.0f + suffixTextSize, textBaseline + textHeight - suffixHeight, textPaint);

        textPaint.setTextSize(bottomTextSize);
        float bottomTextBaseline = getHeight() - (float) Math.tan(15/180f*Math.PI) * getWidth() / 2 - (textPaint.descent() + textPaint.ascent());
        canvas.drawText(getBottomText(), (getWidth() - textPaint.measureText(getBottomText())) / 2.0f, bottomTextBaseline, textPaint);
    }
}
