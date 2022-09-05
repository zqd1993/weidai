package com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr;

import android.text.TextUtils;
import android.util.Log;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class RuYiJieKuanAdgFsdfApi {
    public static String API_BASE_URL = "";

    private static GankServiceRuYiJieKuanAdgFsdf gankServiceRuYiJieKuanAdgFsdf;

    public static GankServiceRuYiJieKuanAdgFsdf getGankService() {
        if (gankServiceRuYiJieKuanAdgFsdf == null && !TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (RuYiJieKuanAdgFsdfApi.class) {
                if (gankServiceRuYiJieKuanAdgFsdf == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankServiceRuYiJieKuanAdgFsdf = XApi.getInstance().getRetrofit(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankServiceRuYiJieKuanAdgFsdf.class);
                }
            }
        }
        return gankServiceRuYiJieKuanAdgFsdf;
    }

    public static String getZc() {
        return RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
