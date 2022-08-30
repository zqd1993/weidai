package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet;

import android.text.TextUtils;
import android.util.Log;

import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class YjjdFqjqApi {
    public static String API_BASE_URL = "";

    private static GankYjjdFqjqService gankYjjdFqjqService;

    public static GankYjjdFqjqService getGankService() {
        if (gankYjjdFqjqService == null && !TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (YjjdFqjqApi.class) {
                if (gankYjjdFqjqService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankYjjdFqjqService = XApi.getInstance().getRetrofit(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankYjjdFqjqService.class);
                }
            }
        }
        return gankYjjdFqjqService;
    }

    public static String getZc() {
        return YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
