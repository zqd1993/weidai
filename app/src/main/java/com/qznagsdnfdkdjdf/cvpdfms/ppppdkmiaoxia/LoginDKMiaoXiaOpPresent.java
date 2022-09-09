package com.qznagsdnfdkdjdf.cvpdfms.ppppdkmiaoxia;

import android.view.View;
import android.widget.TextView;

import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.BaseRespModelDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.ConfigDKMiaoXiaOpModel;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpLoginRespModel;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.HomePageActivityDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.LoginDKMiaoXiaOpActivity;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.DKMiaoXiaOpStaticUtil;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.ToastUtilDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.wwwwdkmiaoxia.CountDownDKMiaoXiaOpTimerUtils;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XPresent;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.NetError;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.XApi;
import com.qznagsdnfdkdjdf.cvpdfms.router.Router;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiSubscriber;

public class LoginDKMiaoXiaOpPresent extends XPresent<LoginDKMiaoXiaOpActivity> {

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
            ApiDKMiaoXiaOp.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>getScheduler())
                    .compose(getV().<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            DKMiaoXiaOpStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisDKMiaoXiaOp.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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

    public void login(String phone, String verificationStr, String ip, String oaidStr) {
            ApiDKMiaoXiaOp.getGankService().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            DKMiaoXiaOpStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDKMiaoXiaOp<DKMiaoXiaOpLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesUtilisDKMiaoXiaOp.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisDKMiaoXiaOp.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesUtilisDKMiaoXiaOp.saveStringIntoPref("ip", ip);
                                    Router.newIntent(getV())
                                            .to(HomePageActivityDKMiaoXiaOp.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastUtilDKMiaoXiaOp.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
    }

    public void sendVerifyCode(String phone, TextView textView) {
            ApiDKMiaoXiaOp.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp>getApiTransformer())
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp>getScheduler())
                    .compose(getV().<BaseRespModelDKMiaoXiaOp>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp>() {
                        @Override
                        protected void onFail(NetError error) {
                            DKMiaoXiaOpStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDKMiaoXiaOp gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastUtilDKMiaoXiaOp.showShort("验证码发送成功");
                                    CountDownDKMiaoXiaOpTimerUtils mCountDownDKMiaoXiaOpTimerUtils = new CountDownDKMiaoXiaOpTimerUtils(textView, 60000, 1000);
                                    mCountDownDKMiaoXiaOpTimerUtils.start();
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
