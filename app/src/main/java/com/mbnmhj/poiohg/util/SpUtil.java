package com.mbnmhj.poiohg.util;

import com.mbnmhj.poiohg.BaseApp;
import com.mbnmhj.poiohg.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpUtil {

    public static void saveInt(String key, int value) {
        BaseApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static String getASVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static int getASVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 历史地址
     */
    public final static String HISTORYADDRESS = "HISTORYADDRESS";
    /**
     * 扫描类型
     */
    public final static String QRCODETYPE = "QRCODETYPE";

    public final static String BARCODE = "BARCODE";

    //    获取默认文件存储目录
    public static String getUpdateFileDir() {
        return "amez/apk/";
    }

    public final static String INTENT_KEY = "update_dialog_values";


    public static int getInt(String key) {
        return BaseApp.getPreferences().getInt(key, 0);
    }

    public static void saveBool(String key, boolean value) {
        BaseApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    /**
     * 将json 数组转换为Map 对象
     * @param jsonString
     * @return
     */
    public static Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

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
