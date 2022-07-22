package com.dfgderv.erterqweq.net;

import android.text.TextUtils;

import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String API_BASE_URL = "http://110.42.64.175:7715";
    public static final String PRIVACY_POLICY = "/profile/mjdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "/profile/mjdk/ysxy.html";

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