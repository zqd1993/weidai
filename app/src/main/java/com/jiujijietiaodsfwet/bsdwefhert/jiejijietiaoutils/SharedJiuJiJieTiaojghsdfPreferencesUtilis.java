package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils;

import com.jiujijietiaodsfwet.bsdwefhert.JiuJiJieTiaojghsdfApp;

public class SharedJiuJiJieTiaojghsdfPreferencesUtilis {


    public static void saveStringIntoPref(String key, String value) {
        JiuJiJieTiaojghsdfApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return JiuJiJieTiaojghsdfApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        JiuJiJieTiaojghsdfApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return JiuJiJieTiaojghsdfApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        JiuJiJieTiaojghsdfApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return JiuJiJieTiaojghsdfApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return JiuJiJieTiaojghsdfApp.getSharedPreferences().contains(key);
    }

}
