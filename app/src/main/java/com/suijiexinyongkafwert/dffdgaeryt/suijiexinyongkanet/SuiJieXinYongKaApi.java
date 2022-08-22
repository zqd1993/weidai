package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet;

import android.text.TextUtils;
import android.util.Log;

import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class SuiJieXinYongKaApi {
    public static String API_BASE_URL = "";

    private static GankServiceSuiJieXinYongKa gankServiceSuiJieXinYongKa;

    public static GankServiceSuiJieXinYongKa getGankService() {
        if (gankServiceSuiJieXinYongKa == null && !TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (SuiJieXinYongKaApi.class) {
                if (gankServiceSuiJieXinYongKa == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankServiceSuiJieXinYongKa = XApi.getInstance().getRetrofit(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankServiceSuiJieXinYongKa.class);
                }
            }
        }
        return gankServiceSuiJieXinYongKa;
    }

    public static String getZc() {
        return SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
