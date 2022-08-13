package com.rtyhdfh.mnzdfgdsg.pppp;

import android.text.TextUtils;

import com.rtyhdfh.mnzdfgdsg.mmmm.BaseRespModelGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.mmmm.GeiNiHuaLoginRespModel;
import com.rtyhdfh.mnzdfgdsg.uuuu.HomePageActivityGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.GeiNiHuaStaticUtil;
import com.rtyhdfh.mnzdfgdsg.mvp.XPresent;
import com.rtyhdfh.mnzdfgdsg.nnnn.ApiGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.nnnn.NetError;
import com.rtyhdfh.mnzdfgdsg.nnnn.XApi;
import com.rtyhdfh.mnzdfgdsg.nnnn.ApiSubscriber;

public class GeiNiHuaMainPresent extends XPresent<HomePageActivityGeiNiHua> {

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
        phone = SharedPreferencesUtilisGeiNiHua.getStringFromPref("phone");
        ip = SharedPreferencesUtilisGeiNiHua.getStringFromPref("ip");
        ApiGeiNiHua.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>getScheduler())
                .compose(getV().<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        GeiNiHuaStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel> gankResults) {
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
