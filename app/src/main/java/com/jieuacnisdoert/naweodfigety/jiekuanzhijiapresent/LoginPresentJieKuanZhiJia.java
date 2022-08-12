package com.jieuacnisdoert.naweodfigety.jiekuanzhijiapresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.BaseRespModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.CompanyInfoModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaLoginRespModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaLoginStatusModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.JieKuanZhiJiaApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.HomePageActivityJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.JieKuanZhiJiaLoginActivity;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStaticUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaToastUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiawidget.JieKuanZhiJiaCountDownTimerUtils;
import com.jieuacnisdoert.naweodfigety.mvp.XPresent;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.NetError;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.XApi;
import com.jieuacnisdoert.naweodfigety.router.Router;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.ApiSubscriber;

public class LoginPresentJieKuanZhiJia extends XPresent<JieKuanZhiJiaLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().getGankData()
                    .compose(XApi.<JieKuanZhiJiaLoginStatusModel>getApiTransformer())
                    .compose(XApi.<JieKuanZhiJiaLoginStatusModel>getScheduler())
                    .compose(getV().<JieKuanZhiJiaLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JieKuanZhiJiaLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            JieKuanZhiJiaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JieKuanZhiJiaLoginStatusModel jieKuanZhiJiaLoginStatusModel) {
                            if (jieKuanZhiJiaLoginStatusModel != null) {
                                if ("1".equals(jieKuanZhiJiaLoginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(jieKuanZhiJiaLoginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(jieKuanZhiJiaLoginStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + jieKuanZhiJiaLoginStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + jieKuanZhiJiaLoginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>getScheduler())
                    .compose(getV().<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>() {
                        @Override
                        protected void onFail(NetError error) {
                            JieKuanZhiJiaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            JieKuanZhiJiaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<JieKuanZhiJiaLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityJieKuanZhiJia.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    JieKuanZhiJiaToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia>getScheduler())
                    .compose(getV().<BaseRespModelJieKuanZhiJia>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia>() {
                        @Override
                        protected void onFail(NetError error) {
                            JieKuanZhiJiaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                JieKuanZhiJiaToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    JieKuanZhiJiaCountDownTimerUtils mJieKuanZhiJiaCountDownTimerUtils = new JieKuanZhiJiaCountDownTimerUtils(textView, 60000, 1000);
                                    mJieKuanZhiJiaCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
