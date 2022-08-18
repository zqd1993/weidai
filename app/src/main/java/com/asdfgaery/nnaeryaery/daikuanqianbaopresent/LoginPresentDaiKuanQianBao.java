package com.asdfgaery.nnaeryaery.daikuanqianbaopresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.BaseRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.CompanyDaiKuanQianBaoInfoModel;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.LoginDaiKuanQianBaoStatusModel;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.LoginRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.DaiKuanQianBaoApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.HomePageDaiKuanQianBaoActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.DaiKuanQianBaoLoginActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.ToastUtilDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.StaticDaiKuanQianBaoUtil;
import com.asdfgaery.nnaeryaery.daikuanqianbaowidget.CountDownDaiKuanQianBaoTimerUtils;
import com.asdfgaery.nnaeryaery.mvp.XPresent;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.NetError;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.XApi;
import com.asdfgaery.nnaeryaery.router.Router;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.ApiSubscriber;

public class LoginPresentDaiKuanQianBao extends XPresent<DaiKuanQianBaoLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DaiKuanQianBaoApi.getGankService().getGankData()
                    .compose(XApi.<LoginDaiKuanQianBaoStatusModel>getApiTransformer())
                    .compose(XApi.<LoginDaiKuanQianBaoStatusModel>getScheduler())
                    .compose(getV().<LoginDaiKuanQianBaoStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginDaiKuanQianBaoStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticDaiKuanQianBaoUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginDaiKuanQianBaoStatusModel loginDaiKuanQianBaoStatusModel) {
                            if (loginDaiKuanQianBaoStatusModel != null) {
                                if ("1".equals(loginDaiKuanQianBaoStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginDaiKuanQianBaoStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginDaiKuanQianBaoStatusModel.getIs_code_register());
                                Log.d("zqd", "loginDiXiDaiKuanStatusModel.getIs_agree_check() = " + loginDaiKuanQianBaoStatusModel.getIs_agree_check() +
                                        "--->loginDiXiDaiKuanStatusModel.getIs_code_register() = " + loginDaiKuanQianBaoStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DaiKuanQianBaoApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticDaiKuanQianBaoUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DaiKuanQianBaoApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>getScheduler())
                    .compose(getV().<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticDaiKuanQianBaoUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaiKuanQianBao<LoginRespModelDaiKuanQianBao> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageDaiKuanQianBaoActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilDaiKuanQianBao.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DaiKuanQianBaoApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelDaiKuanQianBao>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaiKuanQianBao>getScheduler())
                    .compose(getV().<BaseRespModelDaiKuanQianBao>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticDaiKuanQianBaoUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaiKuanQianBao gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilDaiKuanQianBao.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownDaiKuanQianBaoTimerUtils mCountDownDaiKuanQianBaoTimerUtils = new CountDownDaiKuanQianBaoTimerUtils(textView, 60000, 1000);
                                    mCountDownDaiKuanQianBaoTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
