package com.meiwen.speedmw.jiekou;

import android.text.TextUtils;

import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;
import com.meiwen.speedmw.net.XApi;

public class HttpYouBeiApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/yb/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/yb/ysxy.html";
    public static String HTTP_API_URL = "";

    private static InterfaceYouBeiUtils interfaceUtils;

    public static InterfaceYouBeiUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (HttpYouBeiApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"), true).create(InterfaceYouBeiUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
