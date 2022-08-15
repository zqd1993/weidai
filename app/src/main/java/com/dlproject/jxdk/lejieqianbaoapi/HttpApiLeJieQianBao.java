package com.dlproject.jxdk.lejieqianbaoapi;

import com.dlproject.jxdk.net.XApi;

public class HttpApiLeJieQianBao {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/opjlmdk/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/opjlmdk/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.186.217:6602";

    private static LeJieQianBaoInterfaceUtils leJieQianBaoInterfaceUtils;

    public static LeJieQianBaoInterfaceUtils getInterfaceUtils() {
        if (leJieQianBaoInterfaceUtils == null) {
            synchronized (HttpApiLeJieQianBao.class) {
                if (leJieQianBaoInterfaceUtils == null) {
                    leJieQianBaoInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(LeJieQianBaoInterfaceUtils.class);
                }
            }
        }
        return leJieQianBaoInterfaceUtils;
    }
}
