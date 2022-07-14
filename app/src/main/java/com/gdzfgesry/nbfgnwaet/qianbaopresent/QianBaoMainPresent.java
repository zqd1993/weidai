package com.gdzfgesry.nbfgnwaet.qianbaopresent;

import com.gdzfgesry.nbfgnwaet.qianbaomodel.BaseRespQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.QianBaoLoginRespModel;
import com.gdzfgesry.nbfgnwaet.uiqianbao.HomePageActivityQianBao;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.StaticUtilQianBao;
import com.gdzfgesry.nbfgnwaet.mvp.XPresent;
import com.gdzfgesry.nbfgnwaet.netqianbao.QianBaoApi;
import com.gdzfgesry.nbfgnwaet.netqianbao.NetError;
import com.gdzfgesry.nbfgnwaet.netqianbao.XApi;
import com.gdzfgesry.nbfgnwaet.netqianbao.ApiSubscriber;

public class QianBaoMainPresent extends XPresent<HomePageActivityQianBao> {

    private String phone, ip;

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

    public void login() {
        phone = SharedQianBaoPreferencesUtilis.getStringFromPref("phone");
        ip = SharedQianBaoPreferencesUtilis.getStringFromPref("ip");
        QianBaoApi.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespQianBaoModel<QianBaoLoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespQianBaoModel<QianBaoLoginRespModel>>getScheduler())
                .compose(getV().<BaseRespQianBaoModel<QianBaoLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespQianBaoModel<QianBaoLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilQianBao.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespQianBaoModel<QianBaoLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

    public static String gseryhrh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double jjrtyhsrd(Object o) {

        return toDouble(o, 0);
    }

    public static double ertghrsty(Object o, int defaultValue) {
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

    public static long usrhxfgh(Object o, long defaultValue) {
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
