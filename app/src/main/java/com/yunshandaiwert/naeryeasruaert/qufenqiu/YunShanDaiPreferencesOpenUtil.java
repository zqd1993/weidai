package com.yunshandaiwert.naeryeasruaert.qufenqiu;

import com.yunshandaiwert.naeryeasruaert.YunShanDaiMainApp;

public class YunShanDaiPreferencesOpenUtil {

    public static void saveInt(String key, int value) {
        YunShanDaiMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return YunShanDaiMainApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        YunShanDaiMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        YunShanDaiMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return YunShanDaiMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return YunShanDaiMainApp.getPreferences().getBoolean(key, false);
    }

}
