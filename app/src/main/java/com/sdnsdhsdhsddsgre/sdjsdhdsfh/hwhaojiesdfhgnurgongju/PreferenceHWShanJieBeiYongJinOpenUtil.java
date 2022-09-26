package com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju;

import com.sdnsdhsdhsddsgre.sdjsdhdsfh.HWShanJieBeiYongJinApp;

public class PreferenceHWShanJieBeiYongJinOpenUtil {

    public static void saveInt(String key, int value) {
        HWShanJieBeiYongJinApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return HWShanJieBeiYongJinApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        HWShanJieBeiYongJinApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    public static void saveString(String key, String value) {
        HWShanJieBeiYongJinApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return HWShanJieBeiYongJinApp.getPreferences().getString(key, "");
    }

    /**
     * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
     * 根据busybox获取本地Mac
     *
     * @return
     */
    public static String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = "";
        // 如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "网络异常";
        }
        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            result = Mac;
        }
        return result;
    }

    public static boolean getBool(String key) {
        return HWShanJieBeiYongJinApp.getPreferences().getBoolean(key, false);
    }

}
