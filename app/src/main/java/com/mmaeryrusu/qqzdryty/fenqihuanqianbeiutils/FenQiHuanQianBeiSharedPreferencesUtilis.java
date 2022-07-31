package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils;

import com.mmaeryrusu.qqzdryty.AppFenQiHuanQianBei;

public class FenQiHuanQianBeiSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        AppFenQiHuanQianBei.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return AppFenQiHuanQianBei.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        AppFenQiHuanQianBei.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return AppFenQiHuanQianBei.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        AppFenQiHuanQianBei.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return AppFenQiHuanQianBei.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return AppFenQiHuanQianBei.getSharedPreferences().contains(key);
    }

}
