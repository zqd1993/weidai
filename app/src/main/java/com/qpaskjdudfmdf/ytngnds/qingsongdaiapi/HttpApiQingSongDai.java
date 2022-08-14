package com.qpaskjdudfmdf.ytngnds.qingsongdaiapi;

import com.qpaskjdudfmdf.ytngnds.net.XApi;

public class HttpApiQingSongDai {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/voqsdfq/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/voqsdfq/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.190.34:7736";

    private static InterfaceUtilsQingSongDai interfaceUtilsQingSongDai;

    public static InterfaceUtilsQingSongDai getInterfaceUtils() {
        if (interfaceUtilsQingSongDai == null) {
            synchronized (HttpApiQingSongDai.class) {
                if (interfaceUtilsQingSongDai == null) {
                    interfaceUtilsQingSongDai = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsQingSongDai.class);
                }
            }
        }
        return interfaceUtilsQingSongDai;
    }
}
