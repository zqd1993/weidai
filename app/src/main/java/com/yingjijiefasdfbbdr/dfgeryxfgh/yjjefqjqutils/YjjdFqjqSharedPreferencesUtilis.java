package com.yingjijiefasdfbbdr.dfgeryxfgh.yjjefqjqutils;

import com.yingjijiefasdfbbdr.dfgeryxfgh.YjjdFqjqApp;

public class YjjdFqjqSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        YjjdFqjqApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return YjjdFqjqApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        YjjdFqjqApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return YjjdFqjqApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        YjjdFqjqApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return YjjdFqjqApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return YjjdFqjqApp.getSharedPreferences().contains(key);
    }

}
