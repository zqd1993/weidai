package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqpresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.LoginStatusModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.YjjdFqjqApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.HomePageActivityYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqBaseRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.CompanyInfoModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqLoginRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.LoginYjjdFqjqActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.StaticYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.ToastYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqwidget.YjjdFqjqCountDownTimerUtils;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XPresent;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.NetError;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.XApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.router.Router;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.ApiSubscriber;

public class LoginYjjdFqjqPresent extends XPresent<LoginYjjdFqjqActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            YjjdFqjqApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelYjjdFqjq>getApiTransformer())
                    .compose(XApi.<LoginStatusModelYjjdFqjq>getScheduler())
                    .compose(getV().<LoginStatusModelYjjdFqjq>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelYjjdFqjq>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticYjjdFqjqUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelYjjdFqjq loginStatusModelYjjdFqjq) {
                            if (loginStatusModelYjjdFqjq != null) {
                                if ("1".equals(loginStatusModelYjjdFqjq.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelYjjdFqjq.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelYjjdFqjq.getIs_code_register());
                                Log.d("zqd", "loginStatusModelYjjdFqjq.getIs_agree_check() = " + loginStatusModelYjjdFqjq.getIs_agree_check() +
                                        "--->loginStatusModelYjjdFqjq.getIs_code_register() = " + loginStatusModelYjjdFqjq.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            YjjdFqjqApi.getGankService().getCompanyInfo()
                    .compose(XApi.<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>getScheduler())
                    .compose(getV().<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticYjjdFqjqUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip, String oaidStr) {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            YjjdFqjqApi.getGankService().login(phone, verificationStr, "", ip, oaidStr, "OAID")
                    .compose(XApi.<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>getScheduler())
                    .compose(getV().<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticYjjdFqjqUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel<YjjdFqjqLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityYjjdFqjq.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastYjjdFqjqUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            YjjdFqjqApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<YjjdFqjqBaseRespModel>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel>getScheduler())
                    .compose(getV().<YjjdFqjqBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticYjjdFqjqUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastYjjdFqjqUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    YjjdFqjqCountDownTimerUtils mYjjdFqjqCountDownTimerUtils = new YjjdFqjqCountDownTimerUtils(textView, 60000, 1000);
                                    mYjjdFqjqCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
