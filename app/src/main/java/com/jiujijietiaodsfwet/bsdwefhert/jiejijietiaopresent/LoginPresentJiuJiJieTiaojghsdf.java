package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaopresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.HomePageJiuJiJieTiaojghsdfActivity;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.BaseRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.CompanyJiuJiJieTiaojghsdfInfoModel;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.LoginJiuJiJieTiaojghsdfStatusModel;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.LoginRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.JiuJiJieTiaojghsdfApi;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.JiuJiJieTiaojghsdfLoginActivity;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfToastUtil;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.StaticJiuJiJieTiaojghsdfUtil;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaowidget.CountDownJiuJiJieTiaojghsdfTimerUtils;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XPresent;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.NetError;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.XApi;
import com.jiujijietiaodsfwet.bsdwefhert.router.Router;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.ApiSubscriber;

public class LoginPresentJiuJiJieTiaojghsdf extends XPresent<JiuJiJieTiaojghsdfLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieTiaojghsdfApi.getGankService().getGankData()
                    .compose(XApi.<LoginJiuJiJieTiaojghsdfStatusModel>getApiTransformer())
                    .compose(XApi.<LoginJiuJiJieTiaojghsdfStatusModel>getScheduler())
                    .compose(getV().<LoginJiuJiJieTiaojghsdfStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginJiuJiJieTiaojghsdfStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticJiuJiJieTiaojghsdfUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginJiuJiJieTiaojghsdfStatusModel loginJiuJiJieTiaojghsdfStatusModel) {
                            if (loginJiuJiJieTiaojghsdfStatusModel != null) {
                                if ("1".equals(loginJiuJiJieTiaojghsdfStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginJiuJiJieTiaojghsdfStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginJiuJiJieTiaojghsdfStatusModel.getIs_code_register());
                                Log.d("zqd", "loginJiuJiJieTiaojghsdfStatusModel.getIs_agree_check() = " + loginJiuJiJieTiaojghsdfStatusModel.getIs_agree_check() +
                                        "--->loginJiuJiJieTiaojghsdfStatusModel.getIs_code_register() = " + loginJiuJiJieTiaojghsdfStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieTiaojghsdfApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticJiuJiJieTiaojghsdfUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieTiaojghsdfApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>getScheduler())
                    .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticJiuJiJieTiaojghsdfUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJiuJiJieTiaojghsdf<LoginRespModelJiuJiJieTiaojghsdf> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageJiuJiJieTiaojghsdfActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    JiuJiJieTiaojghsdfToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieTiaojghsdfApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf>getApiTransformer())
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf>getScheduler())
                    .compose(getV().<BaseRespModelJiuJiJieTiaojghsdf>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticJiuJiJieTiaojghsdfUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJiuJiJieTiaojghsdf gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                JiuJiJieTiaojghsdfToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownJiuJiJieTiaojghsdfTimerUtils mCountDownJiuJiJieTiaojghsdfTimerUtils = new CountDownJiuJiJieTiaojghsdfTimerUtils(textView, 60000, 1000);
                                    mCountDownJiuJiJieTiaojghsdfTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
