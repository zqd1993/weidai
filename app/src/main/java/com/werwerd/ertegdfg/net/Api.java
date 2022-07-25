package com.werwerd.ertegdfg.net;

import android.text.TextUtils;

import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://bones.huyt78p.com/profile/vivohmjk/zcxy.html ";
    public static final String USER_SERVICE_AGREEMENT= "https://bones.huyt78p.com/profile/vivohmjk/ysxy.html";
    public static final String API_BASE_URL = "http://202.52.144.93:7716";

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
