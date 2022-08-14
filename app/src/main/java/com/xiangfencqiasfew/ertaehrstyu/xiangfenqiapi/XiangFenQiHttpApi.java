package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi;

import com.xiangfencqiasfew.ertaehrstyu.net.XApi;

public class XiangFenQiHttpApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/xykxfqyk/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/xykxfqyk/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.64.111:7738";

    private static XiangFenQiInterfaceUtils xiangFenQiInterfaceUtils;

    public static XiangFenQiInterfaceUtils getInterfaceUtils() {
        if (xiangFenQiInterfaceUtils == null) {
            synchronized (XiangFenQiHttpApi.class) {
                if (xiangFenQiInterfaceUtils == null) {
                    xiangFenQiInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(XiangFenQiInterfaceUtils.class);
                }
            }
        }
        return xiangFenQiInterfaceUtils;
    }
}
