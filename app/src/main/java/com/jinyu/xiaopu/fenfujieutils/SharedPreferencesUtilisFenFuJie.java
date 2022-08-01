package com.jinyu.xiaopu.fenfujieutils;

import com.jinyu.xiaopu.FenFuJieApp;

public class SharedPreferencesUtilisFenFuJie {


    public static void saveStringIntoPref(String key, String value) {
        FenFuJieApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return FenFuJieApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        FenFuJieApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return FenFuJieApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        FenFuJieApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return FenFuJieApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return FenFuJieApp.getSharedPreferences().contains(key);
    }

}
