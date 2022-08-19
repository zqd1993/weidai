package com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.LingHuoJieHuanMainApp;

public class PreferencesOpenUtilLingHuoJieHuan {

    public static void saveInt(String key, int value) {
        LingHuoJieHuanMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return LingHuoJieHuanMainApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        LingHuoJieHuanMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        LingHuoJieHuanMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return LingHuoJieHuanMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return LingHuoJieHuanMainApp.getPreferences().getBoolean(key, false);
    }

}
