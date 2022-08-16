package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi;

import android.text.TextUtils;

import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;

public class HttpApiQuFenQi {
    public static final String ZCXY = "https://bones2.jhgujkg.com/profile/opxfq/zcxy.html";
    public static final String YSXY= "https://bones2.jhgujkg.com/profile/opxfq/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.13.66:7759";

    private static QuFenQiInterfaceUtils quFenQiInterfaceUtils;

    public static QuFenQiInterfaceUtils getInterfaceUtils() {
        if (quFenQiInterfaceUtils == null) {
            synchronized (HttpApiQuFenQi.class) {
                if (quFenQiInterfaceUtils == null) {
                    quFenQiInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(QuFenQiInterfaceUtils.class);
                }
            }
        }
        return quFenQiInterfaceUtils;
    }
}
