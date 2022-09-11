package com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JieDianQianApi {
    public static final String API_BASE_URL = "http://47.98.62.38:7733";
    public static final String PRIVACY_POLICY = "https://openxy.huaqibuy.com/profile/hwtqhdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://openxy.huaqibuy.com/profile/hwtqhdk/ysxy.html";

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
