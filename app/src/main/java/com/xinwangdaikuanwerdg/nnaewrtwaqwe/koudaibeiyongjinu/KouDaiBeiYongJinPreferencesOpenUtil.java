package com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu;

import com.xinwangdaikuanwerdg.nnaewrtwaqwe.MainAppKouDaiBeiYongJin;

public class KouDaiBeiYongJinPreferencesOpenUtil {

    public static void saveInt(String key, int value) {
        MainAppKouDaiBeiYongJin.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainAppKouDaiBeiYongJin.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainAppKouDaiBeiYongJin.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainAppKouDaiBeiYongJin.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainAppKouDaiBeiYongJin.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainAppKouDaiBeiYongJin.getPreferences().getBoolean(key, false);
    }

}
