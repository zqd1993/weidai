package com.srysfghsrty.mkdtyusaert.wanrongxinyongkapresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.BaseRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.CompanyInfoWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.LoginRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.LoginStatusWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.HomePageActivityWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.LoginWanRongXinYongKaActivity;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.StaticWanRongXinYongKaUtil;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.ToastWanRongXinYongKaUtil;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkawidget.CountDownTimerUtilsWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.mvp.XPresent;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.NetError;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.XApi;
import com.srysfghsrty.mkdtyusaert.router.Router;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiSubscriber;

public class LoginWanRongXinYongKaPresent extends XPresent<LoginWanRongXinYongKaActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ApiWanRongXinYongKa.getGankService().getGankData()
                    .compose(XApi.<LoginStatusWanRongXinYongKaModel>getApiTransformer())
                    .compose(XApi.<LoginStatusWanRongXinYongKaModel>getScheduler())
                    .compose(getV().<LoginStatusWanRongXinYongKaModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusWanRongXinYongKaModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticWanRongXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusWanRongXinYongKaModel loginStatusWanRongXinYongKaModel) {
                            if (loginStatusWanRongXinYongKaModel != null) {
                                if ("1".equals(loginStatusWanRongXinYongKaModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusWanRongXinYongKaModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusWanRongXinYongKaModel.getIs_code_register());
                                Log.d("zqd", "loginStatusWanRongXinYongKaModel.getIs_agree_check() = " + loginStatusWanRongXinYongKaModel.getIs_agree_check() +
                                        "--->loginStatusWanRongXinYongKaModel.getIs_code_register() = " + loginStatusWanRongXinYongKaModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ApiWanRongXinYongKa.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>getScheduler())
                    .compose(getV().<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticWanRongXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ApiWanRongXinYongKa.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>getScheduler())
                    .compose(getV().<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticWanRongXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWanRongXinYongKa<LoginRespModelWanRongXinYongKa> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityWanRongXinYongKa.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastWanRongXinYongKaUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ApiWanRongXinYongKa.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelWanRongXinYongKa>getApiTransformer())
                    .compose(XApi.<BaseRespModelWanRongXinYongKa>getScheduler())
                    .compose(getV().<BaseRespModelWanRongXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticWanRongXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWanRongXinYongKa gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastWanRongXinYongKaUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsWanRongXinYongKa mCountDownTimerUtilsWanRongXinYongKa = new CountDownTimerUtilsWanRongXinYongKa(textView, 60000, 1000);
                                    mCountDownTimerUtilsWanRongXinYongKa.start();
                                }
                            }
                        }
                    });
        }
    }

}
