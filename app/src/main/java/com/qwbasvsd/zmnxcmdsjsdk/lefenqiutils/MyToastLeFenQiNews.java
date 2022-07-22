package com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Toast;

import com.qwbasvsd.zmnxcmdsjsdk.LeFenQiNewsMainApp;

public class MyToastLeFenQiNews {

    private static long lastClick = 0;

    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
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

    private MyToastLeFenQiNews() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(LeFenQiNewsMainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int poyitdjty(Context context, float spVal)
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
    public static float werzdh(Context context, float pxVal)
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
    public static float mdtijryter(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
