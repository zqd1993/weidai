package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet;

import android.text.TextUtils;
import android.util.Log;

import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiKuaiDianFenQiDai {
    public static String API_BASE_URL = "";

    private static GankKuaiDianFenQiDaiService gankKuaiDianFenQiDaiService;

    public static GankKuaiDianFenQiDaiService getGankService() {
        if (gankKuaiDianFenQiDaiService == null && !TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (ApiKuaiDianFenQiDai.class) {
                if (gankKuaiDianFenQiDaiService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankKuaiDianFenQiDaiService = XApi.getInstance().getRetrofit(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"), true).create(GankKuaiDianFenQiDaiService.class);
                }
            }
        }
        return gankKuaiDianFenQiDaiService;
    }

    public static String getZc() {
        return SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
