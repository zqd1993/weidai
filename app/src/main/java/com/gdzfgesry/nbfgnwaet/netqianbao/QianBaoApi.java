package com.gdzfgesry.nbfgnwaet.netqianbao;

import android.text.TextUtils;
import android.util.Log;

import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;

/**
 * Created by wanglei on 2016/12/31.
 */

public class QianBaoApi {

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }
    public static String API_BASE_URL = "";

    private static GankQianBaoService gankQianBaoService;

    public static GankQianBaoService getGankService() {
        if (gankQianBaoService == null && !TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            synchronized (QianBaoApi.class) {
                if (gankQianBaoService == null) {
                    Log.d("zqd", "API_BASE_URL = " + API_BASE_URL);
                    gankQianBaoService = XApi.getInstance().getRetrofit(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"), true).create(GankQianBaoService.class);
                }
            }
        }
        return gankQianBaoService;
    }

    public static String rghsxfgh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double utrhsrh(Object o) {

        return toDouble(o, 0);
    }

    public static double nnsrtyhrty(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ukdtyjhst(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public static String getZc() {
        return SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/zc";
    }

    public static String getYs() {
        return SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL") + "/api/ht/ys";
    }

    public static String efgEWT(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double nsrtha(Object o) {

        return toDouble(o, 0);
    }

    public static double serra(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long kkythrdst(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }
}
