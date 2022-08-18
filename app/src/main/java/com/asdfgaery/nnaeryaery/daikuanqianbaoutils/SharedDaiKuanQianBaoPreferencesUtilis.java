package com.asdfgaery.nnaeryaery.daikuanqianbaoutils;

import com.asdfgaery.nnaeryaery.DaiKuanQianBaoApp;

public class SharedDaiKuanQianBaoPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        DaiKuanQianBaoApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return DaiKuanQianBaoApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        DaiKuanQianBaoApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return DaiKuanQianBaoApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        DaiKuanQianBaoApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return DaiKuanQianBaoApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return DaiKuanQianBaoApp.getSharedPreferences().contains(key);
    }

}
