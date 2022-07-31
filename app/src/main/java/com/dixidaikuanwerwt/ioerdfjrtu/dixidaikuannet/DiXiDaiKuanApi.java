package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet;

import android.text.TextUtils;
import android.util.Log;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class DiXiDaiKuanApi {
    public static String API_BASE_URL = "";

    private static GankDiXiDaiKuanService gankDiXiDaiKuanService;

    public static GankDiXiDaiKuanService getGankService() {
        if (gankDiXiDaiKuanService == null && !TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (DiXiDaiKuanApi.class) {
                if (gankDiXiDaiKuanService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankDiXiDaiKuanService = XApi.getInstance().getRetrofit(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankDiXiDaiKuanService.class);
                }
            }
        }
        return gankDiXiDaiKuanService;
    }

    public static String getZc() {
        return SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
