package com.chenqi.lecheng.net;

import android.text.TextUtils;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://gnxys.pycxwl.cn/profile/hjjk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://gnxys.pycxwl.cn/profile/hjjk/ysxy.html";
    public static String API_BASE_URL = "http://117.50.190.34:7706";

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
