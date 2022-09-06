package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu;

import com.linghuojieasdufne.vbdsetrrte.LingHuoJieSvsdFdMainApp;

public class PreferencesOpenUtilLingHuoJieSvsdFd {

    public static void saveInt(String key, int value) {
        LingHuoJieSvsdFdMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return LingHuoJieSvsdFdMainApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        LingHuoJieSvsdFdMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        LingHuoJieSvsdFdMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return LingHuoJieSvsdFdMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return LingHuoJieSvsdFdMainApp.getPreferences().getBoolean(key, false);
    }

}
