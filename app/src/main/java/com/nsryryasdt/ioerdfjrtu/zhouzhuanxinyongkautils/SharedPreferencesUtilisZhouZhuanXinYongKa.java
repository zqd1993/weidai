package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils;

import com.nsryryasdt.ioerdfjrtu.ZhouZhuanXinYongKaApp;

public class SharedPreferencesUtilisZhouZhuanXinYongKa {


    public static void saveStringIntoPref(String key, String value) {
        ZhouZhuanXinYongKaApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return ZhouZhuanXinYongKaApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        ZhouZhuanXinYongKaApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return ZhouZhuanXinYongKaApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        ZhouZhuanXinYongKaApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return ZhouZhuanXinYongKaApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return ZhouZhuanXinYongKaApp.getSharedPreferences().contains(key);
    }

}
