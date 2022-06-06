package com.rtvfbgfh.yuiyjghn.u;

import com.rtvfbgfh.yuiyjghn.RenRenApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SPOpenUtil {

    public static void saveInt(String key, int value) {
        RenRenApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return RenRenApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        RenRenApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        RenRenApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getMonth(String src) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date date = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
            date = format1.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date != null) {
                return format.format(date);
            } else {
                return "";
            }
        }
    }

    public static String getString(String key) {
        return RenRenApp.getPreferences().getString(key, "");
    }

    public static String getTimeNoline(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Date date = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
            date = format1.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date != null) {
                return format.format(date);
            } else {
                return "";
            }
        }
    }


    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }


    public static boolean getBool(String key) {
        return RenRenApp.getPreferences().getBoolean(key, false);
    }

}
