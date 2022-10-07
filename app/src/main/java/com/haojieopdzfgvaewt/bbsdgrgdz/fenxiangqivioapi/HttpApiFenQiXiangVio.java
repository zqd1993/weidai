package com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioapi;

import com.haojieopdzfgvaewt.bbsdgrgdz.net.XApi;

public class HttpApiFenQiXiangVio {
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/ophjdk/zcxy.html";
    public static final String YSXY= "https://xyssml.yiqian888.xyz/profile/ophjdk/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.91.252:6601";

    private static FenQiXiangVioInterfaceUtils fenQiXiangVioInterfaceUtils;

    public static FenQiXiangVioInterfaceUtils getInterfaceUtils() {
        if (fenQiXiangVioInterfaceUtils == null) {
            synchronized (HttpApiFenQiXiangVio.class) {
                if (fenQiXiangVioInterfaceUtils == null) {
                    fenQiXiangVioInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(FenQiXiangVioInterfaceUtils.class);
                }
            }
        }
        return fenQiXiangVioInterfaceUtils;
    }
}
