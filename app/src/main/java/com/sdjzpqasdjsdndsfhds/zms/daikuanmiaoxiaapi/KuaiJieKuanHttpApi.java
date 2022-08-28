package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi;

import com.sdjzpqasdjsdndsfhds.zms.net.XApi;

public class KuaiJieKuanHttpApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/hwxejsd/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/hwxejsd/ysxy.html";
    public static String HTTP_API_URL = "http://47.105.47.183:7733";

    private static InterfaceUtilsKuaiJieKuan interfaceUtils;

    public static InterfaceUtilsKuaiJieKuan getInterfaceUtils() {
        if (interfaceUtils == null) {
            synchronized (KuaiJieKuanHttpApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsKuaiJieKuan.class);
                }
            }
        }
        return interfaceUtils;
    }
}
