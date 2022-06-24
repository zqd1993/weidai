package com.meiwen.speedmw.gongju;

import com.meiwen.speedmw.MainYouBeiApp;

public class PreferencesYouBeiOpenUtil {

    public static void saveInt(String key, int value) {
        MainYouBeiApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainYouBeiApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainYouBeiApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainYouBeiApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainYouBeiApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainYouBeiApp.getPreferences().getBoolean(key, false);
    }

}
