package com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils;

import com.srysfghsrty.mkdtyusaert.WanRongXinYongKaApp;

public class SharedPreferencesUtilisWanRongXinYongKa {


    public static void saveStringIntoPref(String key, String value) {
        WanRongXinYongKaApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return WanRongXinYongKaApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        WanRongXinYongKaApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return WanRongXinYongKaApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        WanRongXinYongKaApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return WanRongXinYongKaApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return WanRongXinYongKaApp.getSharedPreferences().contains(key);
    }

}
