package com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JieDianQianApi {
    public static final String API_BASE_URL = "http://117.50.185.215:7733";
    public static final String PRIVACY_POLICY = "https://gnxys.pycxwl.cn/profile/hwljxyh/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://gnxys.pycxwl.cn/profile/hwljxyh/ysxy.html";

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
