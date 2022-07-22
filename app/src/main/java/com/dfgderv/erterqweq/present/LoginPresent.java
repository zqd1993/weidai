package com.dfgderv.erterqweq.present;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dfgderv.erterqweq.model.BaseRespModel;
import com.dfgderv.erterqweq.model.ConfigModel;
import com.dfgderv.erterqweq.model.LoginRespModel;
import com.dfgderv.erterqweq.ui.HomePageActivity;
import com.dfgderv.erterqweq.ui.LoginActivity;
import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;
import com.dfgderv.erterqweq.utils.StaticUtil;
import com.dfgderv.erterqweq.utils.ToastUtil;
import com.dfgderv.erterqweq.widget.CountDownTimerUtils;
import com.dfgderv.erterqweq.mvp.XPresent;
import com.dfgderv.erterqweq.net.Api;
import com.dfgderv.erterqweq.net.NetError;
import com.dfgderv.erterqweq.net.XApi;
import com.dfgderv.erterqweq.router.Router;
import com.dfgderv.erterqweq.net.ApiSubscriber;

public class LoginPresent extends XPresent<LoginActivity> {

    public void getGankData() {
        if(!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().getGankData()
                    .compose(XApi.<BaseRespModel<ConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<ConfigModel>>getScheduler())
                    .compose(getV().<BaseRespModel<ConfigModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModel<ConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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
    }

    public void login(String phone, String verificationStr, String ip) {
        if(!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
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
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesUtilis.saveStringIntoPref("ip", ip);
                                    StaticUtil.getValue(getV(), HomePageActivity.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if(!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
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
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastUtil.showShort("验证码发送成功");
                                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(textView, 60000, 1000);
                                    mCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}