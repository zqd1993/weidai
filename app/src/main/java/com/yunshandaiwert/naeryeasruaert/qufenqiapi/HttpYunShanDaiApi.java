package com.yunshandaiwert.naeryeasruaert.qufenqiapi;

import com.yunshandaiwert.naeryeasruaert.net.XApi;

public class HttpYunShanDaiApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/xyksyqd/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/xyksyqd/ysxy.html";
    public static String HTTP_API_URL = "http://47.98.62.38:7755";

    private static YunShanDaiInterfaceUtils yunShanDaiInterfaceUtils;

    public static YunShanDaiInterfaceUtils getInterfaceUtils() {
        if (yunShanDaiInterfaceUtils == null) {
            synchronized (HttpYunShanDaiApi.class) {
                if (yunShanDaiInterfaceUtils == null) {
                    yunShanDaiInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(YunShanDaiInterfaceUtils.class);
                }
            }
        }
        return yunShanDaiInterfaceUtils;
    }
}
