package com.aklsfasad.fsjhfkk.present;

import android.view.View;
import android.widget.TextView;

import com.aklsfasad.fsjhfkk.model.BaseRespHuiMinModel;
import com.aklsfasad.fsjhfkk.model.ConfigHuiMinModel;
import com.aklsfasad.fsjhfkk.model.LoginRespHuiMinModel;
import com.aklsfasad.fsjhfkk.ui.HomePageActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.LoginActivityHuiMin;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.ToastUtilHuiMin;
import com.aklsfasad.fsjhfkk.widget.CountDownTimerUtilsHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XPresent;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.XApi;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.net.ApiSubscriber;

public class LoginPresentHuiMin extends XPresent<LoginActivityHuiMin> {

    public void login(String phone, String verificationStr, String ip) {

        Api.getGankService().login(phone, verificationStr, "", ip)
                .compose(XApi.<BaseRespHuiMinModel<LoginRespHuiMinModel>>getApiTransformer())
                .compose(XApi.<BaseRespHuiMinModel<LoginRespHuiMinModel>>getScheduler())
                .compose(getV().<BaseRespHuiMinModel<LoginRespHuiMinModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespHuiMinModel<LoginRespHuiMinModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        StaticUtilHuiMin.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespHuiMinModel<LoginRespHuiMinModel> gankResults) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        if (gankResults != null && gankResults.getCode() == 200) {
                            if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                SharedPreferencesUtilisHuiMin.saveStringIntoPref("phone", phone);
                                SharedPreferencesUtilisHuiMin.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                SharedPreferencesUtilisHuiMin.saveStringIntoPref("ip", ip);
                                Router.newIntent(getV())
                                        .to(HomePageActivityHuiMin.class)
                                        .launch();
                                getV().finish();
                            }
                        } else {
                            if (gankResults.getCode() == 500){
                                ToastUtilHuiMin.showShort(gankResults.getMsg());
                            }
                        }
                    }
                });
    }

    public void getGankData() {
        Api.getGankService().getGankData()
                .compose(XApi.<BaseRespHuiMinModel<ConfigHuiMinModel>>getApiTransformer())
                .compose(XApi.<BaseRespHuiMinModel<ConfigHuiMinModel>>getScheduler())
                .compose(getV().<BaseRespHuiMinModel<ConfigHuiMinModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespHuiMinModel<ConfigHuiMinModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilHuiMin.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespHuiMinModel<ConfigHuiMinModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
//                                ToastUtilHuiMin.showShort("APP_MAIL = " + gankResults.getData().getAppMail());
                                SharedPreferencesUtilisHuiMin.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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

    public void sendVerifyCode(String phone, TextView textView) {
        Api.getGankService().sendVerifyCode(phone)
                .compose(XApi.<BaseRespHuiMinModel>getApiTransformer())
                .compose(XApi.<BaseRespHuiMinModel>getScheduler())
                .compose(getV().<BaseRespHuiMinModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespHuiMinModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilHuiMin.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespHuiMinModel gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200) {
                                ToastUtilHuiMin.showShort("验证码发送成功");
                                CountDownTimerUtilsHuiMin mCountDownTimerUtils = new CountDownTimerUtilsHuiMin(textView, 60000, 1000);
                                mCountDownTimerUtils.start();
                            }
                        }
                    }
                });
    }

}
