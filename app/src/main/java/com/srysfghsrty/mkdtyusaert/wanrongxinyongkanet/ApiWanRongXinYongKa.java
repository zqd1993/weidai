package com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet;

import android.text.TextUtils;
import android.util.Log;

import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiWanRongXinYongKa {
    public static String API_BASE_URL = "";

    private static WanRongXinYongKaGankService wanRongXinYongKaGankService;

    public static WanRongXinYongKaGankService getGankService() {
        if (wanRongXinYongKaGankService == null && !TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            synchronized (ApiWanRongXinYongKa.class) {
                if (wanRongXinYongKaGankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    wanRongXinYongKaGankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"), true).create(WanRongXinYongKaGankService.class);
                }
            }
        }
        return wanRongXinYongKaGankService;
    }

    public static String getZc() {
        return SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
