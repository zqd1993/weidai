package com.mmaeryrusu.qqzdryty.fenqihuanqianbeipresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.CompanyInfoModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginRespModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginStatusFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.FenQiHuanQianBeiApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.HomePageActivityFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.LoginFenQiHuanQianBeiActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.SharedPreferencesFenQiHuanQianBeiUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiStaticUtil;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.CountDownTimerFenQiHuanQianBeiUtils;
import com.mmaeryrusu.qqzdryty.mvp.XPresent;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.router.Router;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;

public class LoginFenQiHuanQianBeiPresent extends XPresent<LoginFenQiHuanQianBeiActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            FenQiHuanQianBeiApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusFenQiHuanQianBeiModel>getApiTransformer())
                    .compose(XApi.<LoginStatusFenQiHuanQianBeiModel>getScheduler())
                    .compose(getV().<LoginStatusFenQiHuanQianBeiModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusFenQiHuanQianBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiHuanQianBeiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusFenQiHuanQianBeiModel loginStatusFenQiHuanQianBeiModel) {
                            if (loginStatusFenQiHuanQianBeiModel != null) {
                                if ("1".equals(loginStatusFenQiHuanQianBeiModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusFenQiHuanQianBeiModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusFenQiHuanQianBeiModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusFenQiHuanQianBeiModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusFenQiHuanQianBeiModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            FenQiHuanQianBeiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiHuanQianBeiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesFenQiHuanQianBeiUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            FenQiHuanQianBeiApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            FenQiHuanQianBeiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<LoginRespModelFenQiHuanQianBei> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesFenQiHuanQianBeiUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesFenQiHuanQianBeiUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityFenQiHuanQianBei.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilFenQiHuanQianBei.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            FenQiHuanQianBeiApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel>getScheduler())
                    .compose(getV().<BaseRespFenQiHuanQianBeiModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiHuanQianBeiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilFenQiHuanQianBei.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerFenQiHuanQianBeiUtils mCountDownTimerFenQiHuanQianBeiUtils = new CountDownTimerFenQiHuanQianBeiUtils(textView, 60000, 1000);
                                    mCountDownTimerFenQiHuanQianBeiUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
