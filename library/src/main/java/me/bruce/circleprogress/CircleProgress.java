package me.bruce.circleprogress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by bruce on 11/4/14.
 */
public class CircleProgress extends BaseProgress {
    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initConstants() {
        super.initConstants();
        default_text_color = Color.WHITE;
    }

    @Override protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        Path path = new Path();
        path.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, Path.Direction.CCW);

        canvas.clipPath(path, Region.Op.INTERSECT);
        paint.setColor(getUnfinishedStrokeColor());
        canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, paint);

        paint.setColor(getFinishedStrokeColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, getHeight() * (1 - getProgressPercentage()), getWidth(), getHeight(), paint);

        String text = getDrawText();
        float textHeight = textPaint.descent() + textPaint.ascent();
        canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);
    }
}
