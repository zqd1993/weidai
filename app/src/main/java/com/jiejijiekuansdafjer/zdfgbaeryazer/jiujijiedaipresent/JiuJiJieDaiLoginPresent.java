package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaipresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiBaseRespModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiCompanyInfoModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiLoginRespModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiLoginStatusModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.JiuJiJieDaiApi;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.JiuJiJieDaiHomePageActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.JiuJiJieDaiLoginActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiStaticUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiToastUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiwidget.JiuJiJieDaiCountDownTimerUtils;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XPresent;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.NetError;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.XApi;
import com.jiejijiekuansdafjer.zdfgbaeryazer.router.Router;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.ApiSubscriber;

public class JiuJiJieDaiLoginPresent extends XPresent<JiuJiJieDaiLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieDaiApi.getGankService().getGankData()
                    .compose(XApi.<JiuJiJieDaiLoginStatusModel>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiLoginStatusModel>getScheduler())
                    .compose(getV().<JiuJiJieDaiLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiuJiJieDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiLoginStatusModel jiuJiJieDaiLoginStatusModel) {
                            if (jiuJiJieDaiLoginStatusModel != null) {
                                if ("1".equals(jiuJiJieDaiLoginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(jiuJiJieDaiLoginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(jiuJiJieDaiLoginStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + jiuJiJieDaiLoginStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + jiuJiJieDaiLoginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieDaiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>getScheduler())
                    .compose(getV().<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiuJiJieDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieDaiApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>getScheduler())
                    .compose(getV().<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            JiuJiJieDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiBaseRespModel<JiuJiJieDaiLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(JiuJiJieDaiHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    JiuJiJieDaiToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieDaiApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<JiuJiJieDaiBaseRespModel>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiBaseRespModel>getScheduler())
                    .compose(getV().<JiuJiJieDaiBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiuJiJieDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiBaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                JiuJiJieDaiToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    JiuJiJieDaiCountDownTimerUtils mJiuJiJieDaiCountDownTimerUtils = new JiuJiJieDaiCountDownTimerUtils(textView, 60000, 1000);
                                    mJiuJiJieDaiCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
