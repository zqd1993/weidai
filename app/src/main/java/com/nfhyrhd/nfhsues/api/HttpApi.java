package com.nfhyrhd.nfhsues.api;

import com.nfhyrhd.nfhsues.net.XApi;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/opbyjqb/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/opbyjqb/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7730";

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
