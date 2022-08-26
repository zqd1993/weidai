package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.XApi;

public class HttpKuaiJieJieKuanNewVoApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/kjk/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/kjk/ysxy.html";
    public static String HTTP_API_URL = "http://47.98.62.38:6604";

    private static InterfaceUtilsKuaiJieJieKuanNewVo interfaceUtilsKouDaiBeiYongJinOp;

    public static InterfaceUtilsKuaiJieJieKuanNewVo getInterfaceUtils() {
        if (interfaceUtilsKouDaiBeiYongJinOp == null) {
            synchronized (HttpKuaiJieJieKuanNewVoApi.class) {
                if (interfaceUtilsKouDaiBeiYongJinOp == null) {
                    interfaceUtilsKouDaiBeiYongJinOp = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceUtilsKuaiJieJieKuanNewVo.class);
                }
            }
        }
        return interfaceUtilsKouDaiBeiYongJinOp;
    }
}
