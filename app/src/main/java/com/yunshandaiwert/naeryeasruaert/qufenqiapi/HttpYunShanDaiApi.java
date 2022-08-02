package com.yunshandaiwert.naeryeasruaert.qufenqiapi;

import com.yunshandaiwert.naeryeasruaert.net.XApi;

public class HttpYunShanDaiApi {
    public static final String ZCXY = "https://htsxy.fhjdhjf.com/profile/xyksyqd/zcxy.html";
    public static final String YSXY= "https://htsxy.fhjdhjf.com/profile/xyksyqd/ysxy.html";
    public static String HTTP_API_URL = "http://45.112.206.58:7755";

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
