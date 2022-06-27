package com.aklsfasad.fsjhfkk.net;

import android.text.TextUtils;

import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://xy.hgy5kg.com/profile/ophmdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://xy.hgy5kg.com/profile/ophmdk/ysxy.html";
    public static final String API_BASE_URL = "http://45.120.154.46:7718";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null && !TextUtils.isEmpty(SharedPreferencesUtilisHuiMin.getStringFromPref("HTTP_API_URL"))) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilisHuiMin.getStringFromPref("HTTP_API_URL"), true).create(GankService.class);
                }
            }
        }
        return gankService;
    }
}
