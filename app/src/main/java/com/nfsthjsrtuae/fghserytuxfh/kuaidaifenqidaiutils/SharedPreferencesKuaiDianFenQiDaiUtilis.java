package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils;

import com.nfsthjsrtuae.fghserytuxfh.KuaiDianFenQiDaiApp;

public class SharedPreferencesKuaiDianFenQiDaiUtilis {


    public static void saveStringIntoPref(String key, String value) {
        KuaiDianFenQiDaiApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return KuaiDianFenQiDaiApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        KuaiDianFenQiDaiApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return KuaiDianFenQiDaiApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        KuaiDianFenQiDaiApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return KuaiDianFenQiDaiApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return KuaiDianFenQiDaiApp.getSharedPreferences().contains(key);
    }

}
