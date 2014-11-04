package me.bruce.circleprogress.example;

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
import android.view.View;

/**
 * Created by bruce on 11/4/14.
 */
public class CircleProgress extends View {
    private static final int DEFAULT_SIZE = 100;

    private Bitmap srcBitmap;
    private Bitmap destBitmap;
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

        srcBitmap = makeSrc(DEFAULT_SIZE);
        destBitmap = makeDst(DEFAULT_SIZE);

        // make a ckeckerboard pattern
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

    public static Bitmap makeAlpha(Bitmap bit) {
        int width =  bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int [] allPixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
        bit.getPixels(allPixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
        myBitmap.setPixels(allPixels, 0, width, 0, 0, width, height);
        return myBitmap;
    }

    private Bitmap makeDst(int size) {
        Bitmap bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, size, size), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    private Bitmap makeSrc(int size) {
        Bitmap bm = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(0, size/3, size, size, p);
        return bm;
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);

        canvas.translate(15, 35);

        int x = 0;
        int y = 0;
        // draw the checker-board pattern
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);
        canvas.drawRect(x, y, x + DEFAULT_SIZE, y + DEFAULT_SIZE, paint);

        // draw the src/dst example into our offscreen bitmap
        int sc = canvas.saveLayer(x, y, x + DEFAULT_SIZE, y + DEFAULT_SIZE, null,
            Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.translate(x, y);
        canvas.drawBitmap(destBitmap, 0, 0, paint);
        paint.setXfermode(mode);
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
