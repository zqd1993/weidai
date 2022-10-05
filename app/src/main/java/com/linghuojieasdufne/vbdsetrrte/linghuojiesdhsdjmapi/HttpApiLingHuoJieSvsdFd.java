package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi;

import com.linghuojieasdufne.vbdsetrrte.net.XApi;

public class HttpApiLingHuoJieSvsdFd {
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/oplhjh/zcxy.html";
    public static final String YSXY= "https://xyssml.yiqian888.xyz/profile/oplhjh/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.91.252:6602";

    private static LingHuoJieSvsdFdInterfaceUtils lingHuoJieSvsdFdInterfaceUtils;

    public static LingHuoJieSvsdFdInterfaceUtils getInterfaceUtils() {
        if (lingHuoJieSvsdFdInterfaceUtils == null) {
            synchronized (HttpApiLingHuoJieSvsdFd.class) {
                if (lingHuoJieSvsdFdInterfaceUtils == null) {
                    lingHuoJieSvsdFdInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(LingHuoJieSvsdFdInterfaceUtils.class);
                }
            }
        }
        return lingHuoJieSvsdFdInterfaceUtils;
    }
}
