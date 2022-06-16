package com.fghyugfg.mjkyhgb.api;

import android.app.Activity;

import com.fghyugfg.mjkyhgb.net.XApi;

public class NewApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/op361dk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/op361dk/ysxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7729";

    private static RenRenInterfaceUtils interfaceUtils;

    private static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    public static RenRenInterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null) {
            synchronized (NewApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(RenRenInterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
