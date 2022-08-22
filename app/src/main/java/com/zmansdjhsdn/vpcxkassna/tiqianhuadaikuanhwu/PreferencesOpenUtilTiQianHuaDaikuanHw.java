package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu;

import com.zmansdjhsdn.vpcxkassna.MainTiQianHuaDaikuanHwApp;

public class PreferencesOpenUtilTiQianHuaDaikuanHw {

    public static void saveInt(String key, int value) {
        MainTiQianHuaDaikuanHwApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainTiQianHuaDaikuanHwApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainTiQianHuaDaikuanHwApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainTiQianHuaDaikuanHwApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainTiQianHuaDaikuanHwApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainTiQianHuaDaikuanHwApp.getPreferences().getBoolean(key, false);
    }

}
