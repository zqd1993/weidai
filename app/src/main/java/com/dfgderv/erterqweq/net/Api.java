package com.dfgderv.erterqweq.net;

import android.text.TextUtils;

import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String API_BASE_URL = "http://202.52.144.94:7715";
    public static final String PRIVACY_POLICY = "https://bones2.jhgujkg.com/profile/vivoayjq/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://bones2.jhgujkg.com/profile/vivoayjq/ysxy.html";

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
