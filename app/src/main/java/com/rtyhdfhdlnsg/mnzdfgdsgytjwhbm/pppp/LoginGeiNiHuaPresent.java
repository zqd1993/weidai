package com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.pppp;

import android.view.View;
import android.widget.TextView;

import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mmmm.BaseRespModelGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mmmm.ConfigGeiNiHuaModel;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mmmm.GeiNiHuaLoginRespModel;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mvp.XActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.HomePageActivityGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.LoginGeiNiHuaActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.utils.GeiNiHuaStaticUtil;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.utils.ToastUtilGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.wwww.CountDownGeiNiHuaTimerUtils;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mvp.XPresent;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.ApiGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.NetError;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.XApi;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.ApiSubscriber;

public class LoginGeiNiHuaPresent extends XPresent<LoginGeiNiHuaActivity> {

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

    public void getGankData() {
            ApiGeiNiHua.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>getScheduler())
                    .compose(getV().<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            GeiNiHuaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelGeiNiHua<ConfigGeiNiHuaModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
                                    if ("0".equals(gankResults.getData().getIsCodeLogin())) {
                                        getV().verificationLl.setVisibility(View.GONE);
                                    } else {
                                        getV().verificationLl.setVisibility(View.VISIBLE);
                                    }
                                    getV().isNeedChecked = "1".equals(gankResults.getData().getIsSelectLogin());
                                    getV().isNeedVerification = "1".equals(gankResults.getData().getIsCodeLogin());
                                    getV().remindCb.setChecked(getV().isNeedChecked);
                                }
                            }
                        }
                    });
    }

    public static String kyufh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double aerghsf(Object o) {

        return toDouble(o, 0);
    }

    public static double ndfhfsf(Object o, int defaultValue) {
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

    public static long bshtrsfg(Object o, long defaultValue) {
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

    public void login(String phone, String verificationStr, String ip) {
            ApiGeiNiHua.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            GeiNiHuaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelGeiNiHua<GeiNiHuaLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisGeiNiHua.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("ip", ip);
                                    GeiNiHuaStaticUtil.getValue((XActivity) getV(), HomePageActivityGeiNiHua.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastUtilGeiNiHua.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
    }

    public void sendVerifyCode(String phone, TextView textView) {
            ApiGeiNiHua.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelGeiNiHua>getApiTransformer())
                    .compose(XApi.<BaseRespModelGeiNiHua>getScheduler())
                    .compose(getV().<BaseRespModelGeiNiHua>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua>() {
                        @Override
                        protected void onFail(NetError error) {
                            GeiNiHuaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelGeiNiHua gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastUtilGeiNiHua.showShort("验证码发送成功");
                                    CountDownGeiNiHuaTimerUtils mCountDownGeiNiHuaTimerUtils = new CountDownGeiNiHuaTimerUtils(textView, 60000, 1000);
                                    mCountDownGeiNiHuaTimerUtils.start();
                                }
                            }
                        }
                    });
    }

    public static String uifyjh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mcghfgh(Object o) {

        return toDouble(o, 0);
    }

    public static double aretgfdgh(Object o, int defaultValue) {
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

    public static long fdytfh(Object o, long defaultValue) {
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
