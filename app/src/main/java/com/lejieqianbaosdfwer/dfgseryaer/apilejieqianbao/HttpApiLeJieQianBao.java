package com.lejieqianbaosdfwer.dfgseryaer.apilejieqianbao;

import com.lejieqianbaosdfwer.dfgseryaer.net.XApi;

public class HttpApiLeJieQianBao {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/voljqb/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/voljqb/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.190.34:7721";

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
