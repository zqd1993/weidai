package com.xvhyrt.ghjtyu.uti;

import com.xvhyrt.ghjtyu.ParentApp;

public class SPFile {

    public static void saveInt(String key, int value) {
        ParentApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return ParentApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        ParentApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        ParentApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return ParentApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return ParentApp.getPreferences().getBoolean(key, false);
    }

}
