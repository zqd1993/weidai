package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi;

import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;

public class HttpApiQuFenQi {
    public static final String ZCXY = "https://opxy.iuoop9.com/profile/xykqfq/zcxy.html";
    public static final String YSXY= "https://opxy.iuoop9.com/profile/xykqfq/ysxy.html";
    public static String HTTP_API_URL = "http://110.42.64.175:7752";

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
