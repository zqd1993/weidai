package com.tryrbdfbv.grtregdfh.utils;

import com.tryrbdfbv.grtregdfh.App;

public class SharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        App.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return App.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        App.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return App.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        App.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return App.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return App.getSharedPreferences().contains(key);
    }

}
