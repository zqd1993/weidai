package com.aklsfasad.fsjhfkk.net;

import android.text.TextUtils;

import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://openxy.huaqibuy.com/profile/ophmdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://openxy.huaqibuy.com/profile/ophmdk/ysxy.html";
    public static final String API_BASE_URL = "http://117.50.185.81:7718";

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
