package com.asdfgaery.nnaeryaery.daikuanqianbaonet;

import android.text.TextUtils;
import android.util.Log;

import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class DaiKuanQianBaoApi {
    public static String API_BASE_URL = "";

    private static GankDaiKuanQianBaoService gankDaiKuanQianBaoService;

    public static GankDaiKuanQianBaoService getGankService() {
        if (gankDaiKuanQianBaoService == null && !TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (DaiKuanQianBaoApi.class) {
                if (gankDaiKuanQianBaoService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankDaiKuanQianBaoService = XApi.getInstance().getRetrofit(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankDaiKuanQianBaoService.class);
                }
            }
        }
        return gankDaiKuanQianBaoService;
    }

    public static String getZc() {
        return SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
