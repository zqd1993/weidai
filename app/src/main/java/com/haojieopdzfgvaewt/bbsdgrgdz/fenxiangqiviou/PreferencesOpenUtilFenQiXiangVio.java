package com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou;

import com.haojieopdzfgvaewt.bbsdgrgdz.MainFenQiXiangVioApp;

public class PreferencesOpenUtilFenQiXiangVio {

    public static void saveInt(String key, int value) {
        MainFenQiXiangVioApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainFenQiXiangVioApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainFenQiXiangVioApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainFenQiXiangVioApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainFenQiXiangVioApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainFenQiXiangVioApp.getPreferences().getBoolean(key, false);
    }

}
