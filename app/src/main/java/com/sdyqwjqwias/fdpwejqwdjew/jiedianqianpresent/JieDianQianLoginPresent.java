package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianpresent;

import android.view.View;
import android.widget.TextView;

import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.BaseRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.ConfigJieDianQianModel;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.LoginRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.HomePageJieDianQianActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.JieDianQianLoginActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.ToastUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianwidget.CountDownJieDianQianTimerUtils;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XPresent;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.JieDianQianApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.ApiSubscriber;

public class JieDianQianLoginPresent extends XPresent<JieDianQianLoginActivity> {

    public void getGankData() {
            JieDianQianApi.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelJieDianQian<ConfigJieDianQianModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian<ConfigJieDianQianModel>>getScheduler())
                    .compose(getV().<BaseRespModelJieDianQian<ConfigJieDianQianModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian<ConfigJieDianQianModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticJieDianQianUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian<ConfigJieDianQianModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    JieDianQianSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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

    public void login(String phone, String verificationStr, String ip) {
            JieDianQianApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>getScheduler())
                    .compose(getV().<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian<LoginRespModelJieDianQian>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticJieDianQianUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian<LoginRespModelJieDianQian> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    JieDianQianSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    JieDianQianSharedPreferencesUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    JieDianQianSharedPreferencesUtilis.saveStringIntoPref("ip", ip);
                                    StaticJieDianQianUtil.getValue(getV(), HomePageJieDianQianActivity.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastUtilJieDianQian.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
    }

    public void sendVerifyCode(String phone, TextView textView) {
            JieDianQianApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelJieDianQian>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian>getScheduler())
                    .compose(getV().<BaseRespModelJieDianQian>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticJieDianQianUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastUtilJieDianQian.showShort("验证码发送成功");
                                    CountDownJieDianQianTimerUtils mCountDownJieDianQianTimerUtils = new CountDownJieDianQianTimerUtils(textView, 60000, 1000);
                                    mCountDownJieDianQianTimerUtils.start();
                                }
                            }
                        }
                    });
    }

}
