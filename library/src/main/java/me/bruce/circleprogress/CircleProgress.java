package me.bruce.circleprogress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by bruce on 11/4/14.
 */
public class CircleProgress extends BaseProgress {
    private Bitmap finishedBitmap;
    private Bitmap unfinishedBitmap;
    private Shader shader;     // background checker-board pattern

    private static final Xfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Bitmap bm = Bitmap.createBitmap(new int[] { 0xFFFFFFFF }, 1, 1,
            Bitmap.Config.RGB_565);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bm.setHasAlpha(true);
        } else {
            bm = makeAlpha(bm);
        }
        shader = new BitmapShader(bm,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT);
        Matrix m = new Matrix();
        m.setScale(6, 6);
        shader.setLocalMatrix(m);
    }

    @Override
    protected void initConstants() {
        super.initConstants();
        default_text_color = Color.WHITE;
    }

    public static Bitmap makeAlpha(Bitmap bit) {
        int width =  bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int [] allPixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        bit.getPixels(allPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
        myBitmap.setPixels(allPixels, 0, width, 0, 0, width, height);
        return myBitmap;
    }

    private Bitmap makeUnfinishedBitmap(int size) {
        Bitmap bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(getUnfinishedStrokeColor());
        c.drawOval(new RectF(0, 0, size, size), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    private Bitmap makeFinishedBitmap(int size) {
        Bitmap bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(getFinishedStrokeColor());
        canvas.drawRect(0, size * (1 - getProgressPercentage()), size, size, paint);
        return bm;
    }

    @Override protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);

        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
            Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        if (finishedBitmap != null && !finishedBitmap.isRecycled()) {
            finishedBitmap.recycle();
        }
        if (unfinishedBitmap != null && !unfinishedBitmap.isRecycled()) {
            unfinishedBitmap.recycle();
        }
        finishedBitmap = makeFinishedBitmap(min_size);
        unfinishedBitmap = makeUnfinishedBitmap(min_size);
        canvas.drawBitmap(unfinishedBitmap, 0, 0, paint);
        paint.setXfermode(mode);
        canvas.drawBitmap(finishedBitmap, 0, 0, paint);
        paint.setXfermode(null);

        String text = getDrawText();
        float textHeight = textPaint.descent() + textPaint.ascent();
        canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);
        if (!isInEditMode()) {
            canvas.restoreToCount(sc);
        }
    }
}
