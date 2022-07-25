package com.rihdkauecgh.plihgnytrvfws.net;

import android.text.TextUtils;

import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static String API_BASE_URL = "http://202.52.144.93:7714";
    public static final String PRIVACY_POLICY = "https://bones.huyt78p.com/profile/jdqdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://bones.huyt78p.com/profile/jdqdk/ysxy.html";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankService.class);
                }
            }
        }
        return gankService;
    }
}
