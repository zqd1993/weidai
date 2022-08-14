package com.dlproject.jxdk.lejieqianbaou;

import com.dlproject.jxdk.LeJieQianBaoMainApp;

public class PreferencesOpenUtilLeJieQianBao {

    public static void saveInt(String key, int value) {
        LeJieQianBaoMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return LeJieQianBaoMainApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        LeJieQianBaoMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        LeJieQianBaoMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return LeJieQianBaoMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return LeJieQianBaoMainApp.getPreferences().getBoolean(key, false);
    }

}
