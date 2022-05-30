package com.werwerd.ertegdfg.net;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {
    public static final String PRIVACY_POLICY = "https://xy.hgy5kg.com/profile/yxyj/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://xy.hgy5kg.com/profile/yxyj/ysxy.html";
    public static final String API_BASE_URL = "http://45.120.154.46:7716";

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
