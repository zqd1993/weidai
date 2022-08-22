package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils;

import com.suijiexinyongkafwert.dffdgaeryt.SuiJieXinYongKaApp;

public class SuiJieXinYongKaSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        SuiJieXinYongKaApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return SuiJieXinYongKaApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        SuiJieXinYongKaApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return SuiJieXinYongKaApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        SuiJieXinYongKaApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return SuiJieXinYongKaApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return SuiJieXinYongKaApp.getSharedPreferences().contains(key);
    }

}
