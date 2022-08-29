package com.qznagsdnfdkdjdf.cvpdfms.ppppdkmiaoxia;

import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.BaseRespModelDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpLoginRespModel;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.HomePageActivityDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.DKMiaoXiaOpStaticUtil;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XPresent;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.NetError;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.XApi;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiSubscriber;

public class DKMiaoXiaOpMainPresent extends XPresent<HomePageActivityDKMiaoXiaOp> {

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
        phone = SharedPreferencesUtilisDKMiaoXiaOp.getStringFromPref("phone");
        ip = SharedPreferencesUtilisDKMiaoXiaOp.getStringFromPref("ip");
        ApiDKMiaoXiaOp.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>getScheduler())
                .compose(getV().<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        DKMiaoXiaOpStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

    public static String iuyfjh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double nxfghzdg(Object o) {

        return toDouble(o, 0);
    }

    public static double ewrdfg(Object o, int defaultValue) {
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

    public static long zdfgb(Object o, long defaultValue) {
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
