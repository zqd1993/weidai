package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet;

import android.text.TextUtils;
import android.util.Log;

import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JiuJiJieTiaojghsdfApi {
    public static String API_BASE_URL = "";

    private static GankJiuJiJieTiaojghsdfService gankJiuJiJieTiaojghsdfService;

    public static GankJiuJiJieTiaojghsdfService getGankService() {
        if (gankJiuJiJieTiaojghsdfService == null && !TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (JiuJiJieTiaojghsdfApi.class) {
                if (gankJiuJiJieTiaojghsdfService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankJiuJiJieTiaojghsdfService = XApi.getInstance().getRetrofit(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankJiuJiJieTiaojghsdfService.class);
                }
            }
        }
        return gankJiuJiJieTiaojghsdfService;
    }

    public static String getZc() {
        return SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
