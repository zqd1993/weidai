package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi;

import com.zmansdjhsdn.vpcxkassna.net.XApi;

public class HttpTiQianHuaDaikuanHwApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/hwtqhdk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/hwtqhdk/ysxy.html";
    public static String HTTP_API_URL = "http://47.98.62.38:7733";

    private static InterfaceUtilsTiQianHuaDaikuanHw interfaceUtilsTiQianHuaDaikuanHw;

    public static InterfaceUtilsTiQianHuaDaikuanHw getInterfaceUtils() {
        if (interfaceUtilsTiQianHuaDaikuanHw == null) {
            synchronized (HttpTiQianHuaDaikuanHwApi.class) {
                if (interfaceUtilsTiQianHuaDaikuanHw == null) {
                    interfaceUtilsTiQianHuaDaikuanHw = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsTiQianHuaDaikuanHw.class);
                }
            }
        }
        return interfaceUtilsTiQianHuaDaikuanHw;
    }
}
