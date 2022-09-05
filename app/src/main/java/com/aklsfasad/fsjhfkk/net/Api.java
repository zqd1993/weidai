package com.aklsfasad.fsjhfkk.net;

import android.text.TextUtils;

import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://gnxys.pycxwl.cn/profile/hmdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://gnxys.pycxwl.cn/profile/hmdk/ysxy.html";
    public static final String API_BASE_URL = "http://117.50.190.34:7740";

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
