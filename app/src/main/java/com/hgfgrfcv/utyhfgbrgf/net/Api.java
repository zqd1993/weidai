package com.hgfgrfcv.utyhfgbrgf.net;

import android.text.TextUtils;
import android.util.Log;

import com.hgfgrfcv.utyhfgbrgf.utils.SharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static String API_BASE_URL = "";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null && !TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (Api.class) {
                if (gankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"));
                    gankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankService.class);
                }
            }
        }
        return gankService;
    }

    public static String getZc(){
        return SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs(){
        return SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
