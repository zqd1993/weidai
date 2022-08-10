package com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet;

import android.text.TextUtils;
import android.util.Log;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.SharedPreferencesFenQiHuanQianBeiUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class FenQiHuanQianBeiApi {
    public static String API_BASE_URL = "";

    private static FenQiHuanQianBeiGankService fenQiHuanQianBeiGankService;

    public static FenQiHuanQianBeiGankService getGankService() {
        if (fenQiHuanQianBeiGankService == null && !TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (FenQiHuanQianBeiApi.class) {
                if (fenQiHuanQianBeiGankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    fenQiHuanQianBeiGankService = XApi.getInstance().getRetrofit(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"), true).create(FenQiHuanQianBeiGankService.class);
                }
            }
        }
        return fenQiHuanQianBeiGankService;
    }

    public static String getZc() {
        return SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
