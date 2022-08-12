package com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils;

import com.jieuacnisdoert.naweodfigety.JieKuanZhiJiaApp;

public class JieKuanZhiJiaSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        JieKuanZhiJiaApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return JieKuanZhiJiaApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        JieKuanZhiJiaApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return JieKuanZhiJiaApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        JieKuanZhiJiaApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return JieKuanZhiJiaApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return JieKuanZhiJiaApp.getSharedPreferences().contains(key);
    }

}
