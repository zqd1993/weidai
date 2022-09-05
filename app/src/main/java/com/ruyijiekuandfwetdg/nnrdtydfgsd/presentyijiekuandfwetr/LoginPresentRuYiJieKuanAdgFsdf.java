package com.ruyijiekuandfwetdg.nnrdtydfgsd.presentyijiekuandfwetr;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.BaseRespModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.CompanyInfoModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginRespModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginStatusModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.RuYiJieKuanAdgFsdfApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.HomePageActivityRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginActivity;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStaticUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfToastUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.widgetyijiekuandfwetr.RuYiJieKuanAdgFsdfCountDownTimerUtils;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XPresent;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetError;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.XApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.router.Router;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.ApiSubscriber;

public class LoginPresentRuYiJieKuanAdgFsdf extends XPresent<RuYiJieKuanAdgFsdfLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RuYiJieKuanAdgFsdfApi.getGankService().getGankData()
                    .compose(XApi.<RuYiJieKuanAdgFsdfLoginStatusModel>getApiTransformer())
                    .compose(XApi.<RuYiJieKuanAdgFsdfLoginStatusModel>getScheduler())
                    .compose(getV().<RuYiJieKuanAdgFsdfLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<RuYiJieKuanAdgFsdfLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            RuYiJieKuanAdgFsdfStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(RuYiJieKuanAdgFsdfLoginStatusModel ruYiJieKuanAdgFsdfLoginStatusModel) {
                            if (ruYiJieKuanAdgFsdfLoginStatusModel != null) {
                                if ("1".equals(ruYiJieKuanAdgFsdfLoginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(ruYiJieKuanAdgFsdfLoginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(ruYiJieKuanAdgFsdfLoginStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + ruYiJieKuanAdgFsdfLoginStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + ruYiJieKuanAdgFsdfLoginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RuYiJieKuanAdgFsdfApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>getScheduler())
                    .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>() {
                        @Override
                        protected void onFail(NetError error) {
                            RuYiJieKuanAdgFsdfStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RuYiJieKuanAdgFsdfApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            RuYiJieKuanAdgFsdfStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<RuYiJieKuanAdgFsdfLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityRuYiJieKuanAdgFsdf.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    RuYiJieKuanAdgFsdfToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RuYiJieKuanAdgFsdfApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf>getScheduler())
                    .compose(getV().<BaseRespModelRuYiJieKuanAdgFsdf>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf>() {
                        @Override
                        protected void onFail(NetError error) {
                            RuYiJieKuanAdgFsdfStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                RuYiJieKuanAdgFsdfToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    RuYiJieKuanAdgFsdfCountDownTimerUtils mRuYiJieKuanAdgFsdfCountDownTimerUtils = new RuYiJieKuanAdgFsdfCountDownTimerUtils(textView, 60000, 1000);
                                    mRuYiJieKuanAdgFsdfCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
