package com.aklsfasad.fsjhfkk.utils;

import android.content.res.Resources;

import com.aklsfasad.fsjhfkk.HuiMinApp;

public class SharedPreferencesUtilisHuiMin {


    public static void saveStringIntoPref(String key, String value) {
        HuiMinApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return HuiMinApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        HuiMinApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return HuiMinApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        HuiMinApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return HuiMinApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return HuiMinApp.getSharedPreferences().contains(key);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        int density = (int) (Resources.getSystem().getDisplayMetrics().density + 0.5f);
        return (int) (0.5f + dpValue * density);
    }

}
