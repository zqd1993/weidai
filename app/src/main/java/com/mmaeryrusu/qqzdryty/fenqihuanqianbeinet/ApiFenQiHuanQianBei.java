package com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet;

import android.text.TextUtils;
import android.util.Log;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiFenQiHuanQianBei {
    public static String API_BASE_URL = "";

    private static GankFenQiHuanQianBeiService gankFenQiHuanQianBeiService;

    public static GankFenQiHuanQianBeiService getGankService() {
        if (gankFenQiHuanQianBeiService == null && !TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (ApiFenQiHuanQianBei.class) {
                if (gankFenQiHuanQianBeiService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankFenQiHuanQianBeiService = XApi.getInstance().getRetrofit(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankFenQiHuanQianBeiService.class);
                }
            }
        }
        return gankFenQiHuanQianBeiService;
    }

    public static String getZc() {
        return FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
