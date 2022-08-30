package com.jinyu.xiaopu.fenfujienet;

/**
 * Created by wanglei on 2016/12/31.
 */

public class FenFuJieApi {
    public static final String API_BASE_URL = "http://106.75.91.252:7758";
    public static final String PRIVACY_POLICY = "https://gnxys.pycxwl.cn/profile/smjk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://gnxys.pycxwl.cn/profile/smjk/ysxy.html";

    private static GankServiceFenFuJie gankServiceFenFuJie;

    public static GankServiceFenFuJie getGankService() {
        if (gankServiceFenFuJie == null) {
            synchronized (FenFuJieApi.class) {
                if (gankServiceFenFuJie == null) {
                    gankServiceFenFuJie = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankServiceFenFuJie.class);
                }
            }
        }
        return gankServiceFenFuJie;
    }
}
