package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi;

import com.sdjzpqasdjsdndsfhds.zms.net.XApi;

public class KuaiJieKuanHttpApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/hwkjk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/hwkjk/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.185.81:7734";

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
