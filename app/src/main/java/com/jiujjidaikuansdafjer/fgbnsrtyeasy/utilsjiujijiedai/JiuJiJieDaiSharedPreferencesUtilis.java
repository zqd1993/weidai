package com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.JiuJiJieDaiApp;

public class JiuJiJieDaiSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        JiuJiJieDaiApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return JiuJiJieDaiApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        JiuJiJieDaiApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return JiuJiJieDaiApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        JiuJiJieDaiApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return JiuJiJieDaiApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return JiuJiJieDaiApp.getSharedPreferences().contains(key);
    }

}
