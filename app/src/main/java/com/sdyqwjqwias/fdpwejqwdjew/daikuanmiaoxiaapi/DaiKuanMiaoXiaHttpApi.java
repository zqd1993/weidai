package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi;

import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;

public class DaiKuanMiaoXiaHttpApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/hwkjk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/hwkjk/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.185.81:7734";

    private static InterfaceUtilsDaiKuanMiaoXia interfaceUtils;

    public static InterfaceUtilsDaiKuanMiaoXia getInterfaceUtils() {
        if (interfaceUtils == null) {
            synchronized (DaiKuanMiaoXiaHttpApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsDaiKuanMiaoXia.class);
                }
            }
        }
        return interfaceUtils;
    }
}
