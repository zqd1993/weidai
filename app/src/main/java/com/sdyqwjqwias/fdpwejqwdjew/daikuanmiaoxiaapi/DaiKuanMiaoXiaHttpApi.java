package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi;

import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;

public class DaiKuanMiaoXiaHttpApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/vodkmx/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/vodkmx/ysxy.html";
    public static String HTTP_API_URL = "http://43.249.30.98:7748";

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
