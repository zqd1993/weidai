package com.aklsfasad.fsjhfkk.api;

import com.aklsfasad.fsjhfkk.net.XApi;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/hmdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/hmdk/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7717";

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
