package com.xvhyrt.ghjtyu.api;

import android.text.TextUtils;

import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/dzdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/dzdk/ysxy.html";
    public static String HTTP_API_URL = "";

    private static InterfaceUtils interfaceUtils;

    public static InterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (HttpApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(PreferencesOpenUtil.getString("HTTP_API_URL"), true).create(InterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
