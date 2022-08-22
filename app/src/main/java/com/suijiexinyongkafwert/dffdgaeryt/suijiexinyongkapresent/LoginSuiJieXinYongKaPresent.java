package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkapresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.SuiJieXinYongKaBaseRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.CompanyInfoModelSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.LoginSuiJieXinYongKaRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.LoginStatusModelSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.HomePageActivitySuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.LoginSuiJieXinYongKaActivity;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StaticSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.ToastSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkawidget.CountDownTimerUtilsSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XPresent;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.SuiJieXinYongKaApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.NetError;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.XApi;
import com.suijiexinyongkafwert.dffdgaeryt.router.Router;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.ApiSubscriber;

public class LoginSuiJieXinYongKaPresent extends XPresent<LoginSuiJieXinYongKaActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            SuiJieXinYongKaApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelSuiJieXinYongKa>getApiTransformer())
                    .compose(XApi.<LoginStatusModelSuiJieXinYongKa>getScheduler())
                    .compose(getV().<LoginStatusModelSuiJieXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelSuiJieXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticSuiJieXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelSuiJieXinYongKa loginStatusModelSuiJieXinYongKa) {
                            if (loginStatusModelSuiJieXinYongKa != null) {
                                if ("1".equals(loginStatusModelSuiJieXinYongKa.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelSuiJieXinYongKa.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelSuiJieXinYongKa.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusModelSuiJieXinYongKa.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusModelSuiJieXinYongKa.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            SuiJieXinYongKaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>getApiTransformer())
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>getScheduler())
                    .compose(getV().<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticSuiJieXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            SuiJieXinYongKaApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>getApiTransformer())
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>getScheduler())
                    .compose(getV().<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticSuiJieXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(SuiJieXinYongKaBaseRespModel<LoginSuiJieXinYongKaRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivitySuiJieXinYongKa.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastSuiJieXinYongKaUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            SuiJieXinYongKaApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel>getApiTransformer())
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel>getScheduler())
                    .compose(getV().<SuiJieXinYongKaBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticSuiJieXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(SuiJieXinYongKaBaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastSuiJieXinYongKaUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsSuiJieXinYongKa mCountDownTimerUtilsSuiJieXinYongKa = new CountDownTimerUtilsSuiJieXinYongKa(textView, 60000, 1000);
                                    mCountDownTimerUtilsSuiJieXinYongKa.start();
                                }
                            }
                        }
                    });
        }
    }

}
