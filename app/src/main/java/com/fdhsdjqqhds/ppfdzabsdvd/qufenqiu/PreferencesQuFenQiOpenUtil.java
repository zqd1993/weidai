package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu;

import com.fdhsdjqqhds.ppfdzabsdvd.QuFenQiMainApp;

public class PreferencesQuFenQiOpenUtil {

    public static void saveInt(String key, int value) {
        QuFenQiMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return QuFenQiMainApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        QuFenQiMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        QuFenQiMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return QuFenQiMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return QuFenQiMainApp.getPreferences().getBoolean(key, false);
    }

}
