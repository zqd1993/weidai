package com.queqianmasdfjiert.bdafgawetr.presentqueqianmaboss;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.LoginStatusModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.HomePageActivityQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.BaseRespQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.CompanyInfoModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.LoginQueQianMaBossRespModel;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.LoginQueQianMaBossActivity;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StaticQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.ToastQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.widgetqueqianmaboss.CountDownTimerUtilsQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.mvp.XPresent;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.NetError;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.XApi;
import com.queqianmasdfjiert.bdafgawetr.router.Router;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiSubscriber;

public class LoginQueQianMaBossPresent extends XPresent<LoginQueQianMaBossActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            ApiQueQianMaBoss.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelQueQianMaBoss>getApiTransformer())
                    .compose(XApi.<LoginStatusModelQueQianMaBoss>getScheduler())
                    .compose(getV().<LoginStatusModelQueQianMaBoss>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelQueQianMaBoss>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticQueQianMaBossUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelQueQianMaBoss loginStatusModelQueQianMaBoss) {
                            if (loginStatusModelQueQianMaBoss != null) {
                                if ("1".equals(loginStatusModelQueQianMaBoss.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelQueQianMaBoss.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelQueQianMaBoss.getIs_code_register());
                                Log.d("zqd", "loginStatusModelQueQianMaBoss.getIs_agree_check() = " + loginStatusModelQueQianMaBoss.getIs_agree_check() +
                                        "--->loginStatusModelQueQianMaBoss.getIs_code_register() = " + loginStatusModelQueQianMaBoss.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            ApiQueQianMaBoss.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>getApiTransformer())
                    .compose(XApi.<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>getScheduler())
                    .compose(getV().<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticQueQianMaBossUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            ApiQueQianMaBoss.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>getScheduler())
                    .compose(getV().<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticQueQianMaBossUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQueQianMaBossModel<LoginQueQianMaBossRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityQueQianMaBoss.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastQueQianMaBossUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            ApiQueQianMaBoss.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespQueQianMaBossModel>getApiTransformer())
                    .compose(XApi.<BaseRespQueQianMaBossModel>getScheduler())
                    .compose(getV().<BaseRespQueQianMaBossModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticQueQianMaBossUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQueQianMaBossModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastQueQianMaBossUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsQueQianMaBoss mCountDownTimerUtilsQueQianMaBoss = new CountDownTimerUtilsQueQianMaBoss(textView, 60000, 1000);
                                    mCountDownTimerUtilsQueQianMaBoss.start();
                                }
                            }
                        }
                    });
        }
    }

}
