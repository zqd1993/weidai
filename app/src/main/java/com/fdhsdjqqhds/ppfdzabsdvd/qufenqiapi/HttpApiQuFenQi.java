package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi;

import android.text.TextUtils;

import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;

public class HttpApiQuFenQi {
    public static final String ZCXY = "/profile/xykqfq/zcxy.html";
    public static final String YSXY= "/profile/xykqfq/ysxy.html";
    public static String HTTP_API_URL = "http://110.42.64.175:7752";

    private static QuFenQiInterfaceUtils quFenQiInterfaceUtils;

    public static QuFenQiInterfaceUtils getInterfaceUtils() {
        if (quFenQiInterfaceUtils == null && !TextUtils.isEmpty(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (HttpApiQuFenQi.class) {
                if (quFenQiInterfaceUtils == null) {
                    quFenQiInterfaceUtils = XApi.getInstance().getRetrofit(PreferencesQuFenQiOpenUtil.getString("HTTP_API_URL"), true).create(QuFenQiInterfaceUtils.class);
                }
            }
        }
        return quFenQiInterfaceUtils;
    }
}
