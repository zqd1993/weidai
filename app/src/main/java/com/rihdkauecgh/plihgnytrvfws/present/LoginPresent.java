package com.rihdkauecgh.plihgnytrvfws.present;

import android.view.View;
import android.widget.TextView;

import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.CompanyInfoModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.model.LoginRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.LoginStatusModel;
import com.rihdkauecgh.plihgnytrvfws.ui.HomePageActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.LoginActivity;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtil;
import com.rihdkauecgh.plihgnytrvfws.utils.ToastUtil;
import com.rihdkauecgh.plihgnytrvfws.widget.CountDownTimerUtils;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;

public class LoginPresent extends XPresent<LoginActivity> {

    public void getGankData() {
        Api.getGankService().getGankData()
                .compose(XApi.<LoginStatusModel>getApiTransformer())
                .compose(XApi.<LoginStatusModel>getScheduler())
                .compose(getV().<LoginStatusModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginStatusModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(LoginStatusModel loginStatusModel) {
                        if (loginStatusModel != null) {
                            if ("1".equals(loginStatusModel.getIs_code_register())) {
                                getV().verificationLl.setVisibility(View.GONE);
                            } else {
                                getV().verificationLl.setVisibility(View.VISIBLE);
                            }
                            getV().isNeedChecked = "0".equals(loginStatusModel.getIs_agree_check());
                            getV().isNeedVerification = "0".equals(loginStatusModel.getIs_code_register());
                            getV().remindCb.setChecked(getV().isNeedChecked);
                        }
                    }
                });
    }

    public void getCompanyInfo() {
        Api.getGankService().getCompanyInfo()
                .compose(XApi.<BaseRespModel<CompanyInfoModel>>getApiTransformer())
                .compose(XApi.<BaseRespModel<CompanyInfoModel>>getScheduler())
                .compose(getV().<BaseRespModel<CompanyInfoModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<CompanyInfoModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel<CompanyInfoModel> loginStatusModel) {
                        if (loginStatusModel != null) {
                            if (loginStatusModel.getData() != null){
                                SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                            }
                        }
                    }
                });
    }

    public void login(String phone, String verificationStr, String ip) {

        Api.getGankService().login(phone, verificationStr, "", ip)
                .compose(XApi.<BaseRespModel<LoginRespModel>>getApiTransformer())
                .compose(XApi.<BaseRespModel<LoginRespModel>>getScheduler())
                .compose(getV().<BaseRespModel<LoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<LoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        StaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel<LoginRespModel> gankResults) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        if (gankResults != null && gankResults.getCode() == 0) {
                            if (gankResults.getData() != null) {
                                SharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                SharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                Router.newIntent(getV())
                                        .to(HomePageActivity.class)
                                        .launch();
                                getV().finish();
                            }
                        } else {
                            if (gankResults.getCode() == 500) {
                                ToastUtil.showShort(gankResults.getMsg());
                            }
                        }
                    }
                });
    }

    public void sendVerifyCode(String phone, TextView textView) {
        Api.getGankService().sendVerifyCode(phone)
                .compose(XApi.<BaseRespModel>getApiTransformer())
                .compose(XApi.<BaseRespModel>getScheduler())
                .compose(getV().<BaseRespModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel gankResults) {
                        if (gankResults != null) {
                            ToastUtil.showShort("验证码" + gankResults.getMsg());
                            if (gankResults.getMsg().contains("成功")) {
                                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(textView, 60000, 1000);
                                mCountDownTimerUtils.start();
                            }
                        }
                    }
                });
    }

}
