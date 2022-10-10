package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiapi;

import com.dshqbzansdk.vpcvlsdksdhayjtop.net.XApi;

public class HttpApiQingSongDai {
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/opayjt/zcxy.html";
    public static final String YSXY= "https://xyssml.yiqian888.xyz/profile/opayjt/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.186.217:6603";


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
