package com.meiwen.speedmw.jiekou;

import android.text.TextUtils;

import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;
import com.meiwen.speedmw.net.XApi;

public class HttpYouBeiApi {
    public static final String ZCXY = "https://htsxy.fhjdhjf.com/profile/yb/zcxy.html";
    public static final String YSXY= "https://htsxy.fhjdhjf.com/profile/yb/ysxy.html";
    public static String HTTP_API_URL = "http://45.112.206.60:7726";

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
