package com.aklsfasad.fsjhfkk.net;

import android.text.TextUtils;

import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "/profile/hmdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "/profile/hmdk/ysxy.html";
    public static final String API_BASE_URL = "http://110.42.64.175:7717";

    private static GankService gankService;

    public static GankService getGankService() {
        if (gankService == null && !TextUtils.isEmpty(SharedPreferencesUtilisHuiMin.getStringFromPref("API_BASE_URL"))) {
            synchronized (Api.class) {
                if (gankService == null) {
                    gankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilisHuiMin.getStringFromPref("API_BASE_URL"), true).create(GankService.class);
                }
            }
        }
        return gankService;
    }
}
