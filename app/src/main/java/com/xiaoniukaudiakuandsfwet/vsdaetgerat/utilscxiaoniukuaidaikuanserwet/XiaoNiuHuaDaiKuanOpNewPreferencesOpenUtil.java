package com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xiaoniukaudiakuandsfwet.vsdaetgerat.XiaoNiuHuaDaiKuanOpNewMainApp;

public class XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil {

    public static void saveInt(String key, int value) {
        XiaoNiuHuaDaiKuanOpNewMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return XiaoNiuHuaDaiKuanOpNewMainApp.getPreferences().getInt(key, 0);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static void saveBool(String key, boolean value) {
        XiaoNiuHuaDaiKuanOpNewMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        XiaoNiuHuaDaiKuanOpNewMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return XiaoNiuHuaDaiKuanOpNewMainApp.getPreferences().getString(key, "");
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int werghxfh(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int ertysdfh(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int eryfhrty(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static boolean getBool(String key) {
        return XiaoNiuHuaDaiKuanOpNewMainApp.getPreferences().getBoolean(key, false);
    }

}
