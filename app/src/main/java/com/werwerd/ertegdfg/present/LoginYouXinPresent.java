package com.werwerd.ertegdfg.present;

import android.view.View;
import android.widget.TextView;

import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.ConfigYouXinModel;
import com.werwerd.ertegdfg.model.LoginRespYouXinModel;
import com.werwerd.ertegdfg.ui.HomePageYouXinActivity;
import com.werwerd.ertegdfg.ui.LoginYouXinActivity;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.utils.ToastYouXinUtil;
import com.werwerd.ertegdfg.widget.CountDownTimerYouXinUtils;
import com.werwerd.ertegdfg.mvp.XPresent;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.XApi;
import com.werwerd.ertegdfg.router.Router;
import com.werwerd.ertegdfg.net.ApiSubscriber;

public class LoginYouXinPresent extends XPresent<LoginYouXinActivity> {

    public void login(String phone, String verificationStr, String ip) {

        Api.getGankService().login(phone, verificationStr, "", ip)
                .compose(XApi.<BaseRespYouXinModel<LoginRespYouXinModel>>getApiTransformer())
                .compose(XApi.<BaseRespYouXinModel<LoginRespYouXinModel>>getScheduler())
                .compose(getV().<BaseRespYouXinModel<LoginRespYouXinModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<LoginRespYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        StaticYouXinUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<LoginRespYouXinModel> gankResults) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        if (gankResults != null && gankResults.getCode() == 200) {
                            if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                SharedPreferencesYouXinUtilis.saveStringIntoPref("phone", phone);
                                SharedPreferencesYouXinUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                SharedPreferencesYouXinUtilis.saveStringIntoPref("ip", ip);
                                Router.newIntent(getV())
                                        .to(HomePageYouXinActivity.class)
                                        .launch();
                                getV().finish();
                            }
                        } else {
                            if (gankResults.getCode() == 500){
                                ToastYouXinUtil.showShort(gankResults.getMsg());
                            }
                        }
                    }
                });
    }

    public void getGankData() {
        Api.getGankService().getGankData()
                .compose(XApi.<BaseRespYouXinModel<ConfigYouXinModel>>getApiTransformer())
                .compose(XApi.<BaseRespYouXinModel<ConfigYouXinModel>>getScheduler())
                .compose(getV().<BaseRespYouXinModel<ConfigYouXinModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<ConfigYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticYouXinUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<ConfigYouXinModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                SharedPreferencesYouXinUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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
                .compose(XApi.<BaseRespYouXinModel>getApiTransformer())
                .compose(XApi.<BaseRespYouXinModel>getScheduler())
                .compose(getV().<BaseRespYouXinModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticYouXinUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespYouXinModel gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200) {
                                ToastYouXinUtil.showShort("验证码发送成功");
                                CountDownTimerYouXinUtils mCountDownTimerUtils = new CountDownTimerYouXinUtils(textView, 60000, 1000);
                                mCountDownTimerUtils.start();
                            }
                        }
                    }
                });
    }

}
