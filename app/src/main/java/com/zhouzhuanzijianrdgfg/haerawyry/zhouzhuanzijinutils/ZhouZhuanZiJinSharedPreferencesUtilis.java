package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils;

import com.zhouzhuanzijianrdgfg.haerawyry.ZhouZhuanZiJinApp;

public class ZhouZhuanZiJinSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        ZhouZhuanZiJinApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return ZhouZhuanZiJinApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        ZhouZhuanZiJinApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return ZhouZhuanZiJinApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        ZhouZhuanZiJinApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return ZhouZhuanZiJinApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return ZhouZhuanZiJinApp.getSharedPreferences().contains(key);
    }

}
