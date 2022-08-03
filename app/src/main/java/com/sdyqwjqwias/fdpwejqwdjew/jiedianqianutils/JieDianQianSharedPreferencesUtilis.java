package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils;

import com.sdyqwjqwias.fdpwejqwdjew.JieDianQianApp;

public class JieDianQianSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        JieDianQianApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return JieDianQianApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        JieDianQianApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return JieDianQianApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        JieDianQianApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return JieDianQianApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return JieDianQianApp.getSharedPreferences().contains(key);
    }

}
