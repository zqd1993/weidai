package com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia;

import android.text.TextUtils;
import android.util.Log;

import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class JieKuanZhiJiaApi {
    public static String API_BASE_URL = "";

    private static GankServiceJieKuanZhiJia gankServiceJieKuanZhiJia;

    public static GankServiceJieKuanZhiJia getGankService() {
        if (gankServiceJieKuanZhiJia == null && !TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (JieKuanZhiJiaApi.class) {
                if (gankServiceJieKuanZhiJia == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankServiceJieKuanZhiJia = XApi.getInstance().getRetrofit(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankServiceJieKuanZhiJia.class);
                }
            }
        }
        return gankServiceJieKuanZhiJia;
    }

    public static String getZc() {
        return JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
