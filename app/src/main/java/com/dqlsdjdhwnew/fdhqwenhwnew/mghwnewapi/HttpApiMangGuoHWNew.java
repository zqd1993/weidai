package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi;

import com.dqlsdjdhwnew.fdhqwenhwnew.net.XApi;

public class HttpApiMangGuoHWNew {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/hwmgdk/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/hwmgdk/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.64.111:7707";

    private static FenMangGuoHWNewInterfaceUtils fenMangGuoHWNewInterfaceUtils;

    public static FenMangGuoHWNewInterfaceUtils getInterfaceUtils() {
        if (fenMangGuoHWNewInterfaceUtils == null) {
            synchronized (HttpApiMangGuoHWNew.class) {
                if (fenMangGuoHWNewInterfaceUtils == null) {
                    fenMangGuoHWNewInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(FenMangGuoHWNewInterfaceUtils.class);
                }
            }
        }
        return fenMangGuoHWNewInterfaceUtils;
    }
}
