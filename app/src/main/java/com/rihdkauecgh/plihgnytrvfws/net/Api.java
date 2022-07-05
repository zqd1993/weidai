package com.rihdkauecgh.plihgnytrvfws.net;

import android.text.TextUtils;

import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static String API_BASE_URL = "http://45.120.154.46:7745";
    public static final String PRIVACY_POLICY = "https://xy.hgy5kg.com/profile/opzsdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://xy.hgy5kg.com/profile/opzsdk/ysxy.html";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null && !TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankService.class);
                }
            }
        }
        return gankService;
    }
}
