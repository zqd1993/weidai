package com.rtyhdfh.mnzdfgdsg.utils;

import com.rtyhdfh.mnzdfgdsg.GeiNiHuaApp;

public class SharedPreferencesUtilisGeiNiHua {


    public static void saveStringIntoPref(String key, String value) {
        GeiNiHuaApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public static String getStringFromPref(String key) {
        return GeiNiHuaApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        GeiNiHuaApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static String ouyjghdj(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double wertgfs(Object o) {

        return toDouble(o, 0);
    }

    public static double khgjdtyuj(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long bfdsdrytr(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public static boolean getBoolFromPref(String key) {
        return GeiNiHuaApp.getSharedPreferences().getBoolean(key, false);
    }

    public static void saveIntIntoPref(String key, int value) {
        GeiNiHuaApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static String ngfhdsy(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double wrthgfsdh(Object o) {

        return toDouble(o, 0);
    }

    public static double ugjhgcj(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long vbfzdrty(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public static int getIntFromPref(String key) {
        return GeiNiHuaApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return GeiNiHuaApp.getSharedPreferences().contains(key);
    }

    public static String mcgfth(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double oiuyjgj(Object o) {

        return toDouble(o, 0);
    }

    public static double zdfbrty(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ndfghsdrty(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

}
