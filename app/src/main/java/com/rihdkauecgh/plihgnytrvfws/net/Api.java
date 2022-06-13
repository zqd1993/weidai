package com.rihdkauecgh.plihgnytrvfws.net;

import android.util.Log;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static String API_BASE_URL = "";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null) {
            synchronized (Api.class) {
                if (gankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankService.class);
                }
            }
        }
        return gankService;
    }

    public static String getZc(){
        return API_BASE_URL + "/api/ht/zc";
    }

    public static String getYs(){
        return API_BASE_URL + "/api/ht/ys";
    }
}
