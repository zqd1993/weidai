package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau;

import com.sdyqwjqwias.fdpwejqwdjew.MainDaiKuanMiaoXiaApp;

public class PreferencesOpenUtilDaiKuanMiaoXia {

    public static void saveInt(String key, int value) {
        MainDaiKuanMiaoXiaApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainDaiKuanMiaoXiaApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainDaiKuanMiaoXiaApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainDaiKuanMiaoXiaApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainDaiKuanMiaoXiaApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainDaiKuanMiaoXiaApp.getPreferences().getBoolean(key, false);
    }

}
