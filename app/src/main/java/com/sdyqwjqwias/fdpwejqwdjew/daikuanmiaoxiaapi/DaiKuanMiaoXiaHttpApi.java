package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi;

import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;

public class DaiKuanMiaoXiaHttpApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/opjtjqb/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/opjtjqb/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.186.217:6601";

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
