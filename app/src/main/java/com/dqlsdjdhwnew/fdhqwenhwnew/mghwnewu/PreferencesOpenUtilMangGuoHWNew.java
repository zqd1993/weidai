package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu;

import com.dqlsdjdhwnew.fdhqwenhwnew.MainMangGuoHWNewApp;

public class PreferencesOpenUtilMangGuoHWNew {

    public static void saveInt(String key, int value) {
        MainMangGuoHWNewApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainMangGuoHWNewApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainMangGuoHWNewApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainMangGuoHWNewApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainMangGuoHWNewApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainMangGuoHWNewApp.getPreferences().getBoolean(key, false);
    }

}
