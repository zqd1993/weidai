package com.suishijie.ndjufgjhdrty.present;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.suishijie.ndjufgjhdrty.model.BaseRespModel;
import com.suishijie.ndjufgjhdrty.model.CompanyInfoModel;
import com.suishijie.ndjufgjhdrty.model.LoginRespModel;
import com.suishijie.ndjufgjhdrty.model.LoginStatusModel;
import com.suishijie.ndjufgjhdrty.ui.HomePageActivity;
import com.suishijie.ndjufgjhdrty.ui.LoginActivity;
import com.suishijie.ndjufgjhdrty.utils.SharedPreferencesUtilis;
import com.suishijie.ndjufgjhdrty.utils.StaticUtil;
import com.suishijie.ndjufgjhdrty.utils.ToastUtil;
import com.suishijie.ndjufgjhdrty.widget.CountDownTimerUtils;
import com.suishijie.ndjufgjhdrty.mvp.XPresent;
import com.suishijie.ndjufgjhdrty.net.Api;
import com.suishijie.ndjufgjhdrty.net.NetError;
import com.suishijie.ndjufgjhdrty.net.XApi;
import com.suishijie.ndjufgjhdrty.router.Router;
import com.suishijie.ndjufgjhdrty.net.ApiSubscriber;

public class LoginPresent extends XPresent<LoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModel>getApiTransformer())
                    .compose(XApi.<LoginStatusModel>getScheduler())
                    .compose(getV().<LoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModel loginStatusModel) {
                            if (loginStatusModel != null) {
                                if ("1".equals(loginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModel<CompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<CompanyInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModel<CompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<CompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<CompanyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModel<LoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<LoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModel<LoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<LoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<LoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModel>getApiTransformer())
                    .compose(XApi.<BaseRespModel>getScheduler())
                    .compose(getV().<BaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(textView, 60000, 1000);
                                    mCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
