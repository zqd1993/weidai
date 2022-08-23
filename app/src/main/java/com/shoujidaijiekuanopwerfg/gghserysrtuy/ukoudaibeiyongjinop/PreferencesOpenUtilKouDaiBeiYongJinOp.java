package com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.MainKouDaiBeiYongJinOpApp;

public class PreferencesOpenUtilKouDaiBeiYongJinOp {

    public static void saveInt(String key, int value) {
        MainKouDaiBeiYongJinOpApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainKouDaiBeiYongJinOpApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainKouDaiBeiYongJinOpApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainKouDaiBeiYongJinOpApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainKouDaiBeiYongJinOpApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainKouDaiBeiYongJinOpApp.getPreferences().getBoolean(key, false);
    }

}
