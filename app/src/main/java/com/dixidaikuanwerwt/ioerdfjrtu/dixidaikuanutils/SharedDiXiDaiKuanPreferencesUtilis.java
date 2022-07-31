package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils;

import com.dixidaikuanwerwt.ioerdfjrtu.DiXiDaiKuanApp;

public class SharedDiXiDaiKuanPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        DiXiDaiKuanApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return DiXiDaiKuanApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        DiXiDaiKuanApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return DiXiDaiKuanApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        DiXiDaiKuanApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return DiXiDaiKuanApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return DiXiDaiKuanApp.getSharedPreferences().contains(key);
    }

}
