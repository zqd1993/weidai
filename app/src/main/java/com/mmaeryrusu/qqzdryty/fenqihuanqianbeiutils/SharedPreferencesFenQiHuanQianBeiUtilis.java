package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils;

import com.mmaeryrusu.qqzdryty.FenQiHuanQianBeiApp;

public class SharedPreferencesFenQiHuanQianBeiUtilis {


    public static void saveStringIntoPref(String key, String value) {
        FenQiHuanQianBeiApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return FenQiHuanQianBeiApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        FenQiHuanQianBeiApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return FenQiHuanQianBeiApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        FenQiHuanQianBeiApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return FenQiHuanQianBeiApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return FenQiHuanQianBeiApp.getSharedPreferences().contains(key);
    }

}
