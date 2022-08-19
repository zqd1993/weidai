package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.XApi;

public class HttpApiFenQiXiangVio {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/opxfq/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/opxfq/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.91.252:6666";

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
