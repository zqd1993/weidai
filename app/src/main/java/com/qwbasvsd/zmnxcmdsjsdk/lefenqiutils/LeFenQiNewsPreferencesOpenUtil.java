package com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils;

import android.content.Context;
import android.util.TypedValue;

import com.qwbasvsd.zmnxcmdsjsdk.LeFenQiNewsMainApp;

public class LeFenQiNewsPreferencesOpenUtil {

    public static void saveInt(String key, int value) {
        LeFenQiNewsMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return LeFenQiNewsMainApp.getPreferences().getInt(key, 0);
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

    public static void saveBool(String key, boolean value) {
        LeFenQiNewsMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        LeFenQiNewsMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return LeFenQiNewsMainApp.getPreferences().getString(key, "");
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int mjkrtuyfgh(Context context, float spVal)
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
    public static float werzdhg(Context context, float pxVal)
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
    public static float rtyfgh(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static boolean getBool(String key) {
        return LeFenQiNewsMainApp.getPreferences().getBoolean(key, false);
    }

}
