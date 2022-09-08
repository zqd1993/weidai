package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanpresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.BaseRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.CompanyDiXiDaiKuanInfoModel;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.LoginDiXiDaiKuanStatusModel;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.LoginRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.DiXiDaiKuanApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.HomePageDiXiDaiKuanActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.DiXiDaiKuanLoginActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanToastUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.StaticDiXiDaiKuanUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanwidget.CountDownDiXiDaiKuanTimerUtils;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XPresent;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetError;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.XApi;
import com.dixidaikuanwerwt.ioerdfjrtu.router.Router;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.ApiSubscriber;

public class LoginPresentDiXiDaiKuan extends XPresent<DiXiDaiKuanLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DiXiDaiKuanApi.getGankService().getGankData()
                    .compose(XApi.<LoginDiXiDaiKuanStatusModel>getApiTransformer())
                    .compose(XApi.<LoginDiXiDaiKuanStatusModel>getScheduler())
                    .compose(getV().<LoginDiXiDaiKuanStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginDiXiDaiKuanStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticDiXiDaiKuanUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginDiXiDaiKuanStatusModel loginDiXiDaiKuanStatusModel) {
                            if (loginDiXiDaiKuanStatusModel != null) {
                                if ("1".equals(loginDiXiDaiKuanStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginDiXiDaiKuanStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginDiXiDaiKuanStatusModel.getIs_code_register());
                                Log.d("zqd", "loginDiXiDaiKuanStatusModel.getIs_agree_check() = " + loginDiXiDaiKuanStatusModel.getIs_agree_check() +
                                        "--->loginDiXiDaiKuanStatusModel.getIs_code_register() = " + loginDiXiDaiKuanStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DiXiDaiKuanApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticDiXiDaiKuanUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip, String oaidStr) {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DiXiDaiKuanApi.getGankService().login(phone, verificationStr, "", ip, oaidStr, "OAID")
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>getScheduler())
                    .compose(getV().<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticDiXiDaiKuanUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan<LoginRespModelDiXiDaiKuan> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageDiXiDaiKuanActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    DiXiDaiKuanToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DiXiDaiKuanApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelDiXiDaiKuan>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan>getScheduler())
                    .compose(getV().<BaseRespModelDiXiDaiKuan>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticDiXiDaiKuanUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                DiXiDaiKuanToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownDiXiDaiKuanTimerUtils mCountDownDiXiDaiKuanTimerUtils = new CountDownDiXiDaiKuanTimerUtils(textView, 60000, 1000);
                                    mCountDownDiXiDaiKuanTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
