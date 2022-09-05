package com.jinyu.xiaopu.fenfujiepresent;

import android.view.View;
import android.widget.TextView;

import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieConfigModel;
import com.jinyu.xiaopu.fenfujiemodel.LoginRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujieui.HomePageFenFuJieActivity;
import com.jinyu.xiaopu.fenfujieui.LoginFenFuJieActivity;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.fenfujieutils.ToastFenFuJieUtil;
import com.jinyu.xiaopu.fenfujiewidget.CountDownFenFuJieTimerUtils;
import com.jinyu.xiaopu.mvp.XPresent;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujienet.NetError;
import com.jinyu.xiaopu.fenfujienet.XApi;
import com.jinyu.xiaopu.fenfujienet.ApiSubscriber;

public class LoginFenFuJiePresent extends XPresent<LoginFenFuJieActivity> {

    public void getGankData() {
            FenFuJieApi.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelFenFuJie<FenFuJieConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelFenFuJie<FenFuJieConfigModel>>getScheduler())
                    .compose(getV().<BaseRespModelFenFuJie<FenFuJieConfigModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<FenFuJieConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticFenFuJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFenFuJie<FenFuJieConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisFenFuJie.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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

    public void login(String phone, String verificationStr, String ip, String oaid) {
            FenFuJieApi.getGankService().login(phone, verificationStr, "", ip, oaid)
                    .compose(XApi.<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>getApiTransformer())
                    .compose(XApi.<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>getScheduler())
                    .compose(getV().<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<LoginRespModelFenFuJie>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticFenFuJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFenFuJie<LoginRespModelFenFuJie> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesUtilisFenFuJie.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisFenFuJie.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesUtilisFenFuJie.saveStringIntoPref("ip", ip);
                                    StaticFenFuJieUtil.getValue(getV(), HomePageFenFuJieActivity.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastFenFuJieUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
    }

    public void sendVerifyCode(String phone, TextView textView) {
            FenFuJieApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelFenFuJie>getApiTransformer())
                    .compose(XApi.<BaseRespModelFenFuJie>getScheduler())
                    .compose(getV().<BaseRespModelFenFuJie>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFenFuJie>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticFenFuJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFenFuJie gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastFenFuJieUtil.showShort("验证码发送成功");
                                    CountDownFenFuJieTimerUtils mCountDownFenFuJieTimerUtils = new CountDownFenFuJieTimerUtils(textView, 60000, 1000);
                                    mCountDownFenFuJieTimerUtils.start();
                                }
                            }
                        }
                    });
    }

}
