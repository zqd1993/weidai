package com.lpsydsnl.jtxqchbb.api;

import android.text.TextUtils;

import com.lpsydsnl.jtxqchbb.net.XApi;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/tfqdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/tfqdk/ysxy.html";
    public static String HTTP_API_URL = "45.112.206.60:7705";

    private static InterfaceUtils interfaceUtils;

    public static InterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(HTTP_API_URL)) {
            synchronized (HttpApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
