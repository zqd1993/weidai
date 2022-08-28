package com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss;

import android.text.TextUtils;
import android.util.Log;

import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiQueQianMaBoss {
    public static String API_BASE_URL = "";

    private static GankQueQianMaBossService gankQueQianMaBossService;

    public static GankQueQianMaBossService getGankService() {
        if (gankQueQianMaBossService == null && !TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            synchronized (ApiQueQianMaBoss.class) {
                if (gankQueQianMaBossService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankQueQianMaBossService = XApi.getInstance().getRetrofit(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"), true).create(GankQueQianMaBossService.class);
                }
            }
        }
        return gankQueQianMaBossService;
    }

    public static String getZc() {
        return SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
