package com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class BarLeFenQiNewsView extends View {
    public BarLeFenQiNewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public BarLeFenQiNewsView(Context context) {
        super(context);
    }
}
