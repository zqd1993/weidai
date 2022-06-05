package com.xvhyrt.ghjtyu.api;

import com.xvhyrt.ghjtyu.net.XApi;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/dzdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/dzdk/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7721";

    private static InterfaceUtils interfaceUtils;

    public static InterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null) {
            synchronized (HttpApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
