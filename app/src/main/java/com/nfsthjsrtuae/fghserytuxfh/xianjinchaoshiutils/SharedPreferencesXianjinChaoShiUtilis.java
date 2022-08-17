package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils;

import com.nfsthjsrtuae.fghserytuxfh.XianjinChaoShiApp;

public class SharedPreferencesXianjinChaoShiUtilis {


    public static void saveStringIntoPref(String key, String value) {
        XianjinChaoShiApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return XianjinChaoShiApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        XianjinChaoShiApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return XianjinChaoShiApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        XianjinChaoShiApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return XianjinChaoShiApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return XianjinChaoShiApp.getSharedPreferences().contains(key);
    }

}
