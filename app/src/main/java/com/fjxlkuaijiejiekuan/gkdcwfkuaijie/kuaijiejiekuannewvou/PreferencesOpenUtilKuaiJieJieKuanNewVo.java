package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.MainKuaiJieJieKuanNewVoApp;

public class PreferencesOpenUtilKuaiJieJieKuanNewVo {

    public static void saveInt(String key, int value) {
        MainKuaiJieJieKuanNewVoApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return MainKuaiJieJieKuanNewVoApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        MainKuaiJieJieKuanNewVoApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        MainKuaiJieJieKuanNewVoApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return MainKuaiJieJieKuanNewVoApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return MainKuaiJieJieKuanNewVoApp.getPreferences().getBoolean(key, false);
    }

}
