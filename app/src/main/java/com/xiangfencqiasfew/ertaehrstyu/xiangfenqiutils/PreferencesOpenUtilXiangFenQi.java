package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils;

import com.xiangfencqiasfew.ertaehrstyu.MainAppXiangFenQi;

public class PreferencesOpenUtilXiangFenQi {

    public static void saveInt(String key, int value) {
        MainAppXiangFenQi.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainAppXiangFenQi.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainAppXiangFenQi.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainAppXiangFenQi.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainAppXiangFenQi.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainAppXiangFenQi.getPreferences().getBoolean(key, false);
    }

}
