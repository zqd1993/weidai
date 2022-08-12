package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet;

import android.text.TextUtils;
import android.util.Log;

import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JiuJiJieDaiApi {
    public static String API_BASE_URL = "";

    private static JiuJiJieDaiGankService jiuJiJieDaiGankService;

    public static JiuJiJieDaiGankService getGankService() {
        if (jiuJiJieDaiGankService == null && !TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (JiuJiJieDaiApi.class) {
                if (jiuJiJieDaiGankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    jiuJiJieDaiGankService = XApi.getInstance().getRetrofit(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(JiuJiJieDaiGankService.class);
                }
            }
        }
        return jiuJiJieDaiGankService;
    }

    public static String getZc() {
        return JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
