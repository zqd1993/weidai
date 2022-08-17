package com.pwekqha.itmgs.koudaibeiyongjinapi;

import com.pwekqha.itmgs.net.XApi;

public class HttpKouDaiBeiYongJinApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/hwkjk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/hwkjk/ysxy.html";
    public static String HTTP_API_URL = "http://43.249.30.98:7748";

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
