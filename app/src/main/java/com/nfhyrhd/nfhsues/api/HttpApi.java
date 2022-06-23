package com.nfhyrhd.nfhsues.api;

import android.text.TextUtils;
import android.util.Log;

import com.nfhyrhd.nfhsues.net.XApi;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/opbyjqb/zcxy.html";
    public static final String YSXY = "https://xy.hgy5kg.com/profile/opbyjqb/ysxy.html";
    public static String HTTP_API_URL = "";

    private static InterfaceUtils interfaceUtils;

    public static InterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(HTTP_API_URL)) {
            synchronized (HttpApi.class) {
                if (interfaceUtils == null) {
                    Log.d("zqd", "HTTP_API_URL = " + HTTP_API_URL);
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
