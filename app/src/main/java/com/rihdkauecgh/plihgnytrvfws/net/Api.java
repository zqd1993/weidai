package com.rihdkauecgh.plihgnytrvfws.net;

import android.text.TextUtils;

import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String API_BASE_URL = "http://45.120.154.46:7714";
    public static final String PRIVACY_POLICY = "https://xy.hgy5kg.com/profile/yjxyd/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://xy.hgy5kg.com/profile/yjxyd/ysxy.html";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null && !TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"), true).create(GankService.class);
                }
            }
        }
        return gankService;
    }
}
