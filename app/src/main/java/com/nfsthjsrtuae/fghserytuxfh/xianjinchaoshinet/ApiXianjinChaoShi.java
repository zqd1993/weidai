package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet;

import android.text.TextUtils;
import android.util.Log;

import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiXianjinChaoShi {
    public static String API_BASE_URL = "";

    private static GankXianjinChaoShiService gankXianjinChaoShiService;

    public static GankXianjinChaoShiService getGankService() {
        if (gankXianjinChaoShiService == null && !TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (ApiXianjinChaoShi.class) {
                if (gankXianjinChaoShiService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankXianjinChaoShiService = XApi.getInstance().getRetrofit(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"), true).create(GankXianjinChaoShiService.class);
                }
            }
        }
        return gankXianjinChaoShiService;
    }

    public static String getZc() {
        return SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
