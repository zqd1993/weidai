package com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss;

import com.queqianmasdfjiert.bdafgawetr.QueQianMaBossApp;

public class SharedPreferencesUtilisQueQianMaBoss {


    public static void saveStringIntoPref(String key, String value) {
        QueQianMaBossApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return QueQianMaBossApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        QueQianMaBossApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return QueQianMaBossApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        QueQianMaBossApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return QueQianMaBossApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return QueQianMaBossApp.getSharedPreferences().contains(key);
    }

}
