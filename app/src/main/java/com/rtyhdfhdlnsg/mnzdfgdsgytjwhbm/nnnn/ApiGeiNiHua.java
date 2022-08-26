package com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn;

/**
 * Created by wanglei on 2016/12/31.
 */

public class ApiGeiNiHua {
    public static final String API_BASE_URL = "http://117.50.185.215:7710";
    public static final String PRIVACY_POLICY = "https://gnxys.pycxwl.cn/profile/hwyjdk/zcxy.html";
    public static final String USER_SERVICE_AGREEMENT= "https://gnxys.pycxwl.cn/profile/hwyjdk/ysxy.html";

    private static GankGeiNiHuaService gankGeiNiHuaService;

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

    public static GankGeiNiHuaService getGankService() {
        if (gankGeiNiHuaService == null) {
            synchronized (ApiGeiNiHua.class) {
                if (gankGeiNiHuaService == null) {
                    gankGeiNiHuaService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(GankGeiNiHuaService.class);
                }
            }
        }
        return gankGeiNiHuaService;
    }

    public static String aegsb(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mfgy(Object o) {

        return toDouble(o, 0);
    }

    public static double uytethtgd(Object o, int defaultValue) {
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

    public static long werregsr(Object o, long defaultValue) {
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
