package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi;

import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;

public class DaiKuanMiaoXiaHttpApi {
    public static String OPPO_URL = "http://47.98.62.38:6601";
    public static String VIVO_URL = "http://117.50.190.34:7726";
    public static final String VIVO_ZCXY = "https://gnxys.pycxwl.cn/profile/vohjjk/zcxy.html";
    public static final String VIVO_YSXY = "https://gnxys.pycxwl.cn/profile/vohjjk/ysxy.html";
    public static final String OPPO_ZCXY = "https://gnxys.pycxwl.cn/profile/vohjjk/zcxy.html";
    public static final String OPPO_YSXY = "https://gnxys.pycxwl.cn/profile/vohjjk/ysxy.html";
    public static final String ZCXY = OPPO_ZCXY;
    public static final String YSXY = OPPO_YSXY;
    public static String HTTP_API_URL = OPPO_URL;

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
