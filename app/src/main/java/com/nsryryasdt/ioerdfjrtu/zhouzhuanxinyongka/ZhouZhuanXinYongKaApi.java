package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka;

import android.text.TextUtils;
import android.util.Log;

import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ZhouZhuanXinYongKaApi {
    public static String API_BASE_URL = "";

    private static ZhouZhuanXinYongKaGankService zhouZhuanXinYongKaGankService;

    public static ZhouZhuanXinYongKaGankService getGankService() {
        if (zhouZhuanXinYongKaGankService == null && !TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            synchronized (ZhouZhuanXinYongKaApi.class) {
                if (zhouZhuanXinYongKaGankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    zhouZhuanXinYongKaGankService = XApi.getInstance().getRetrofit(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"), true).create(ZhouZhuanXinYongKaGankService.class);
                }
            }
        }
        return zhouZhuanXinYongKaGankService;
    }

    public static String getZc() {
        return SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
