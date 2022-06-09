package com.pcierecommended.upgradedoccurrences.jiekou;

import com.pcierecommended.upgradedoccurrences.net.XApi;

public class HttpYouBeiApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/yb/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/yb/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7726";

    private static InterfaceYouBeiUtils interfaceUtils;

    public static InterfaceYouBeiUtils getInterfaceUtils() {
        if (interfaceUtils == null) {
            synchronized (HttpYouBeiApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceYouBeiUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
