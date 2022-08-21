package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet;

import android.text.TextUtils;
import android.util.Log;

import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ZhouZhuanZiJinApi {
    public static String API_BASE_URL = "";

    private static ZhouZhuanZiJinGankService zhouZhuanZiJinGankService;

    public static ZhouZhuanZiJinGankService getGankService() {
        if (zhouZhuanZiJinGankService == null && !TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (ZhouZhuanZiJinApi.class) {
                if (zhouZhuanZiJinGankService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    zhouZhuanZiJinGankService = XApi.getInstance().getRetrofit(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(ZhouZhuanZiJinGankService.class);
                }
            }
        }
        return zhouZhuanZiJinGankService;
    }

    public static String getZc() {
        return ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }
}
