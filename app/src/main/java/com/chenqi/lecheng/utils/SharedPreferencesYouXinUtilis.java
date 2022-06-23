package com.chenqi.lecheng.utils;

import com.chenqi.lecheng.YouXinApp;

public class SharedPreferencesYouXinUtilis {


    public static void saveStringIntoPref(String key, String value) {
        YouXinApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return YouXinApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        YouXinApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return YouXinApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        YouXinApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return YouXinApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return YouXinApp.getSharedPreferences().contains(key);
    }

}
