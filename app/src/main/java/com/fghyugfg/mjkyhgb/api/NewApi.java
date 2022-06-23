package com.fghyugfg.mjkyhgb.api;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.fghyugfg.mjkyhgb.net.XApi;

public class NewApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/op361dk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/op361dk/ysxy.html";
    public static String HTTP_API_URL = "";

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
        if (interfaceUtils == null && !TextUtils.isEmpty(HTTP_API_URL)) {
            synchronized (NewApi.class) {
                if (interfaceUtils == null) {
                    Log.d("zqd", "HTTP_API_URL = " + HTTP_API_URL);
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(RenRenInterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }
}
