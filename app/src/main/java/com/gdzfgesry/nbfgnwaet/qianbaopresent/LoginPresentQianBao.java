package com.gdzfgesry.nbfgnwaet.qianbaopresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gdzfgesry.nbfgnwaet.qianbaomodel.BaseRespQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.QianBaoCompanyInfoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.QianBaoLoginRespModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.LoginStatusQianBaoModel;
import com.gdzfgesry.nbfgnwaet.uiqianbao.HomePageActivityQianBao;
import com.gdzfgesry.nbfgnwaet.uiqianbao.LoginQianBaoActivity;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.StaticUtilQianBao;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.ToastQianBaoUtil;
import com.gdzfgesry.nbfgnwaet.qianbaowidget.QianBaoCountDownTimerUtils;
import com.gdzfgesry.nbfgnwaet.mvp.XPresent;
import com.gdzfgesry.nbfgnwaet.netqianbao.QianBaoApi;
import com.gdzfgesry.nbfgnwaet.netqianbao.NetError;
import com.gdzfgesry.nbfgnwaet.netqianbao.XApi;
import com.gdzfgesry.nbfgnwaet.router.Router;
import com.gdzfgesry.nbfgnwaet.netqianbao.ApiSubscriber;

public class LoginPresentQianBao extends XPresent<LoginQianBaoActivity> {

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
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            QianBaoApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusQianBaoModel>getApiTransformer())
                    .compose(XApi.<LoginStatusQianBaoModel>getScheduler())
                    .compose(getV().<LoginStatusQianBaoModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusQianBaoModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilQianBao.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusQianBaoModel loginStatusQianBaoModel) {
                            if (loginStatusQianBaoModel != null) {
                                if ("1".equals(loginStatusQianBaoModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusQianBaoModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusQianBaoModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusQianBaoModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusQianBaoModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            QianBaoApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>getScheduler())
                    .compose(getV().<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilQianBao.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQianBaoModel<QianBaoCompanyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedQianBaoPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public static String uaergzserh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double kkdtryhare(Object o) {

        return toDouble(o, 0);
    }

    public static double hhaertwe(Object o, int defaultValue) {
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

    public static long aerwtgzdf(Object o, long defaultValue) {
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
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            QianBaoApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespQianBaoModel<QianBaoLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespQianBaoModel<QianBaoLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespQianBaoModel<QianBaoLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQianBaoModel<QianBaoLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilQianBao.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQianBaoModel<QianBaoLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedQianBaoPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedQianBaoPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityQianBao.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastQianBaoUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public static String vaergterh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double uusrthaer(Object o) {

        return toDouble(o, 0);
    }

    public static double jjsrtyar(Object o, int defaultValue) {
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

    public static long ggeasryhry(Object o, long defaultValue) {
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

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            QianBaoApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespQianBaoModel>getApiTransformer())
                    .compose(XApi.<BaseRespQianBaoModel>getScheduler())
                    .compose(getV().<BaseRespQianBaoModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQianBaoModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilQianBao.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQianBaoModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastQianBaoUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    QianBaoCountDownTimerUtils mQianBaoCountDownTimerUtils = new QianBaoCountDownTimerUtils(textView, 60000, 1000);
                                    mQianBaoCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

    public static String mgfjdrtyhs(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ertxfghsrt(Object o) {

        return toDouble(o, 0);
    }

    public static double rtuyhhxh(Object o, int defaultValue) {
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

    public static long tryrgdzfh(Object o, long defaultValue) {
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
