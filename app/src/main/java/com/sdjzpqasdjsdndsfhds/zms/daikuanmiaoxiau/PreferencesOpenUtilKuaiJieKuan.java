package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau;

import com.sdjzpqasdjsdndsfhds.zms.MainKuaiJieKuanApp;

public class PreferencesOpenUtilKuaiJieKuan {

    public static void saveInt(String key, int value) {
        MainKuaiJieKuanApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainKuaiJieKuanApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainKuaiJieKuanApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainKuaiJieKuanApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainKuaiJieKuanApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainKuaiJieKuanApp.getPreferences().getBoolean(key, false);
    }

}
