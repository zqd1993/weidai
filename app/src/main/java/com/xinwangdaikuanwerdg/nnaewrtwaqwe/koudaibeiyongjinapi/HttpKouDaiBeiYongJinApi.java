package com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinapi;

import com.xinwangdaikuanwerdg.nnaewrtwaqwe.net.XApi;

public class HttpKouDaiBeiYongJinApi {
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/opxwdk/zcxy.html";
    public static final String YSXY= "https://xyssml.yiqian888.xyz/profile/opxwdk/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.91.252:6603";

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
