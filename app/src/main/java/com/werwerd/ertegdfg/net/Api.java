package com.werwerd.ertegdfg.net;

import android.text.TextUtils;

import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://gnxys.pycxwl.cn/profile/opdejsd/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://gnxys.pycxwl.cn/profile/opdejsd/ysxy.html";
    public static final String API_BASE_URL = "http://106.75.13.66:7743";

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
