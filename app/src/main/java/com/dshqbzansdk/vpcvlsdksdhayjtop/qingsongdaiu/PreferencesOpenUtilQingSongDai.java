package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu;

import com.dshqbzansdk.vpcvlsdksdhayjtop.MainAppQingSongDai;

public class PreferencesOpenUtilQingSongDai {

    public static void saveInt(String key, int value) {
        MainAppQingSongDai.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainAppQingSongDai.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainAppQingSongDai.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainAppQingSongDai.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainAppQingSongDai.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainAppQingSongDai.getPreferences().getBoolean(key, false);
    }

}
