package com.xvhyrt.ghjtyu.api;

import android.text.TextUtils;

import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;

public class HttpApi {
    public static final String ZCXY = "https://bones.huyt78p.com/profile/vivohmjk/zcxy.html";
    public static final String YSXY= "https://bones.huyt78p.com/profile/vivohmjk/ysxy.html ";
    public static String HTTP_API_URL = "http://43.249.30.98:7748";

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
