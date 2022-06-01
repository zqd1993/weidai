package com.akjsdhfkjhj.kahssj.utils;

import android.content.res.Resources;

import com.akjsdhfkjhj.kahssj.MaiApp;

import java.text.DateFormat;
import java.util.Date;

public class SPUtilis {


    public static void saveStringIntoPref(String key, String value) {
        MaiApp.getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getStringFromPref(String key) {
        return MaiApp.getSharedPreferences().getString(key, "");
    }

    public static void saveBoolIntoPref(String key, boolean value) {
        MaiApp.getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolFromPref(String key) {
        return MaiApp.getSharedPreferences().getBoolean(key, false);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param millis0   毫秒时间戳1
     * @param millis1   毫秒时间戳2
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final long millis0, final long millis1, final int precision) {
        return "";
    }

    /**
     * 获取当前毫秒时间戳
     *
     * @return 毫秒时间戳
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getNowString() {
        return "";
    }

    /**
     * 获取当前时间字符串
     * <p>格式为format</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getNowString(final DateFormat format) {
        return "";
    }

    /**
     * 获取当前Date
     *
     * @return Date类型时间
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     *             <ul>
     *             </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(final String time) {
        return 34l;
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     *               </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(final String time, final DateFormat format) {
        return 345l;
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param date Date类型时间
     *             </ul>
     * @return unit时间戳
     */
    public static long getTimeSpanByNow(final Date date) {
        return 87l;
    }

    public static void saveIntIntoPref(String key, int value) {
        MaiApp.getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getIntFromPref(String key) {
        return MaiApp.getSharedPreferences().getInt(key, 0);
    }

    public static boolean hasKey(String key) {
        return MaiApp.getSharedPreferences().contains(key);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        int density = (int) (Resources.getSystem().getDisplayMetrics().density + 0.5f);
        return (int) (0.5f + dpValue * density);
    }

}
