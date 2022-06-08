package com.xvhyrt.ghjtyu.net;

public class WangLuoApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/dzdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/dzdk/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7721";

    private static JieKouGongJu interfaceUtils;

    public static JieKouGongJu getInterfaceUtils() {
        if (interfaceUtils == null) {
            synchronized (WangLuoApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(JieKouGongJu.class);
                }
            }
        }
        return interfaceUtils;
    }
}
