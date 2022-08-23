package com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.XApi;

public class HttpKouDaiBeiYongJinOpApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/opsjdjk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/opsjdjk/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.185.81:7740";

    private static InterfaceUtilsKouDaiBeiYongJinOp interfaceUtilsKouDaiBeiYongJinOp;

    public static InterfaceUtilsKouDaiBeiYongJinOp getInterfaceUtils() {
        if (interfaceUtilsKouDaiBeiYongJinOp == null) {
            synchronized (HttpKouDaiBeiYongJinOpApi.class) {
                if (interfaceUtilsKouDaiBeiYongJinOp == null) {
                    interfaceUtilsKouDaiBeiYongJinOp = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsKouDaiBeiYongJinOp.class);
                }
            }
        }
        return interfaceUtilsKouDaiBeiYongJinOp;
    }
}
