package com.github.lzyzsd.circleprogress;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by bruce on 14-11-6.
 */
public final class Utils
{

    private Utils()
    {
    }

    public static float dp2px(Resources resources, float dp)
    {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp)
    {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static Bitmap getBitmap(Context context, int drawableId)
    {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        return Utils.getBitmap(drawable);
    }

    public static Bitmap getBitmap(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        else if (drawable instanceof VectorDrawableCompat || drawable instanceof VectorDrawable)
        {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                                                drawable.getIntrinsicHeight(),
                                                Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        else
        {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }
}
