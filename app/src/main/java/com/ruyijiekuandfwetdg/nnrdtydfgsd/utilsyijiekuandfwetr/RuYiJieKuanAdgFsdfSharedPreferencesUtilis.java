package com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.RuYiJieKuanAdgFsdfApp;

public class RuYiJieKuanAdgFsdfSharedPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        RuYiJieKuanAdgFsdfApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return RuYiJieKuanAdgFsdfApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        RuYiJieKuanAdgFsdfApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return RuYiJieKuanAdgFsdfApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        RuYiJieKuanAdgFsdfApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return RuYiJieKuanAdgFsdfApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return RuYiJieKuanAdgFsdfApp.getSharedPreferences().contains(key);
    }

}
