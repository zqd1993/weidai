package com.xvhyrt.ghjtyu.api;

import android.text.TextUtils;

import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;

public class HttpApi {
    public static final String ZCXY = "/profile/dzdk/zcxy.html";
    public static final String YSXY= "/profile/dzdk/ysxy.html";
    public static String HTTP_API_URL = "";

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
