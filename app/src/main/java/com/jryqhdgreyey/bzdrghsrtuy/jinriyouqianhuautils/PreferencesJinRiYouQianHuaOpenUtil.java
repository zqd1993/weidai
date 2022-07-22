package com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuautils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import com.jryqhdgreyey.bzdrghsrtuy.JinRiYouQianHuaMainApp;

public class PreferencesJinRiYouQianHuaOpenUtil {

    public static void saveInt(String key, int value) {
        JinRiYouQianHuaMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return JinRiYouQianHuaMainApp.getPreferences().getInt(key, 0);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public static void saveBool(String key, boolean value) {
        JinRiYouQianHuaMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        JinRiYouQianHuaMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return JinRiYouQianHuaMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return JinRiYouQianHuaMainApp.getPreferences().getBoolean(key, false);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap kdtyidtyhsrty(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap eartgzdfhrstu(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

}
