package com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JieDianQianApi {
    public static final String API_BASE_URL = "http://45.112.206.55:7733";
    public static final String PRIVACY_POLICY = "https://htsxy.fhjdhjf.com/profile/hwljxyh/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://htsxy.fhjdhjf.com/profile/hwljxyh/ysxy.html";

    private static GankServiceJieDianQian gankServiceJieDianQian;

    public static GankServiceJieDianQian getGankService() {
        if (gankServiceJieDianQian == null) {
            synchronized (JieDianQianApi.class) {
                if (gankServiceJieDianQian == null) {
                    gankServiceJieDianQian = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankServiceJieDianQian.class);
                }
            }
        }
        return gankServiceJieDianQian;
    }
}
