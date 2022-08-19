package com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.XApi;

public class HttpLingHuoJieHuanApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/oplhjh/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/oplhjh/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.91.252:6602";

    private static InterfaceUtilsLingHuoJieHuan interfaceUtilsLingHuoJieHuan;

    public static InterfaceUtilsLingHuoJieHuan getInterfaceUtils() {
        if (interfaceUtilsLingHuoJieHuan == null) {
            synchronized (HttpLingHuoJieHuanApi.class) {
                if (interfaceUtilsLingHuoJieHuan == null) {
                    interfaceUtilsLingHuoJieHuan = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsLingHuoJieHuan.class);
                }
            }
        }
        return interfaceUtilsLingHuoJieHuan;
    }
}
