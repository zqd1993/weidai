package com.fdhsdjqqhds.ppfdzabsdvd.api;

import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;

public class HttpApi {
    public static final String ZCXY = "https://opxy.iuoop9.com/profile/xykqfq/zcxy.html";
    public static final String YSXY= "https://opxy.iuoop9.com/profile/xykqfq/ysxy.html";
    public static String HTTP_API_URL = "http://110.42.64.175:7752";

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
