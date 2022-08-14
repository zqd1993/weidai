package com.dlproject.jxdk.lejieqianbaoapi;

import com.dlproject.jxdk.net.XApi;

public class HttpApiLeJieQianBao {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/voljqb/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/voljqb/ysxy.html";
    public static String HTTP_API_URL = "http://43.249.30.98:7748";

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
