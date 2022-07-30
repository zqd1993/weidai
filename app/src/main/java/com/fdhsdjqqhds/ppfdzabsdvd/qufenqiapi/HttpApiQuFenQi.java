package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi;

import android.text.TextUtils;

import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;

public class HttpApiQuFenQi {
    public static final String ZCXY = "https://htsxy.fhjdhjf.com/profile/xykqfq/zcxy.html";
    public static final String YSXY= "https://htsxy.fhjdhjf.com/profile/xykqfq/zcxy.html";
    public static String HTTP_API_URL = "http://43.249.30.98:7748";

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
