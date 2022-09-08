package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi;

import com.newmazhaocaiewkfd.drngfs.net.XApi;

public class ZhaoCaiAdfmHttpApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/vozcmdk/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/vozcmdk/ysxy.html";
    public static String HTTP_API_URL = "http://121.41.122.164:6610";

    private static ZhaoCaiAdfmterfaceUtils zhaoCaiAdfmterfaceUtils;

    public static ZhaoCaiAdfmterfaceUtils getInterfaceUtils() {
        if (zhaoCaiAdfmterfaceUtils == null) {
            synchronized (ZhaoCaiAdfmHttpApi.class) {
                if (zhaoCaiAdfmterfaceUtils == null) {
                    zhaoCaiAdfmterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(ZhaoCaiAdfmterfaceUtils.class);
                }
            }
        }
        return zhaoCaiAdfmterfaceUtils;
    }
}
