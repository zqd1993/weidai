package com.pwekqha.itmgs.koudaibeiyongjinapi;

import com.pwekqha.itmgs.net.XApi;

public class HttpKouDaiBeiYongJinApi {
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/vokdbyj/zcxy.html";
    public static final String YSXY= "https://xyssml.yiqian888.xyz/profile/vokdbyj/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.13.66:6604";

    private static InterfaceKouDaiBeiYongJinUtils interfaceKouDaiBeiYongJinUtils;

    public static InterfaceKouDaiBeiYongJinUtils getInterfaceUtils() {
        if (interfaceKouDaiBeiYongJinUtils == null) {
            synchronized (HttpKouDaiBeiYongJinApi.class) {
                if (interfaceKouDaiBeiYongJinUtils == null) {
                    interfaceKouDaiBeiYongJinUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceKouDaiBeiYongJinUtils.class);
                }
            }
        }
        return interfaceKouDaiBeiYongJinUtils;
    }
}
