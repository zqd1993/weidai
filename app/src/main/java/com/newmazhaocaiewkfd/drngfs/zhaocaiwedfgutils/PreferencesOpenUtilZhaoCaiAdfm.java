package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils;

import com.newmazhaocaiewkfd.drngfs.MainAppZhaoCaiAdfm;

public class PreferencesOpenUtilZhaoCaiAdfm {

    public static void saveInt(String key, int value) {
        MainAppZhaoCaiAdfm.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainAppZhaoCaiAdfm.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainAppZhaoCaiAdfm.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainAppZhaoCaiAdfm.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainAppZhaoCaiAdfm.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainAppZhaoCaiAdfm.getPreferences().getBoolean(key, false);
    }

}
