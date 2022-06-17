package com.xg.qdk.u;

import android.content.Context;

import com.xg.qdk.BaseApp;

public class PreferencesStaticOpenUtil {

    private Context mContext;


    private int option1 = 0, option2 = 0, option3 = 0;

    private int defaultPos1, defaultPos2, defaultPos3;

    private boolean first = true, second = true, third = true;

    //    private CityEntity receAddress;
    private boolean isInitOK = false;
    private boolean isInitTimeClicked = false;

    private boolean isInitListener;

    private String fileName;

    private static final char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60,
            61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
            -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    private PreferencesStaticOpenUtil() {
    }

    public static void saveInt(String key, int value) {
        BaseApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return BaseApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        BaseApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        BaseApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return BaseApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return BaseApp.getPreferences().getBoolean(key, false);
    }

}
