package com.github.lzyzsd.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by KhaledLela on 9/27/17.
 */

public abstract class BaseProgress extends View{
    /**
     * decimalformat for formatting
     */
    protected DecimalFormat mFormat;

    /**
     * the number of decimal digits this formatter uses
     */
    protected int digits = 0;

    public BaseProgress(Context context) {
        super(context);
    }

    public BaseProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    abstract void initByAttributes(TypedArray attributes) ;
    public void setProgressFloating(int digits){
        this.digits = digits;
        setFormatter();
    }

    protected void setFormatter() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                b.append(".");
            b.append("0");
        }
        mFormat = new DecimalFormat("###,###,###,##0" + b.toString());
    }
}
