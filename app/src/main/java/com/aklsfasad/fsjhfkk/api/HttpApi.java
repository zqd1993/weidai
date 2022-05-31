package com.aklsfasad.fsjhfkk;

import com.aklsfasad.fsjhfkk.net.GankService;
import com.aklsfasad.fsjhfkk.net.XApi;

public class HttpApi {
    public static final String PRIVACY_POLICY = "https://xy.hgy5kg.com/profile/hmdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://xy.hgy5kg.com/profile/hmdk/ysxy.html";
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
