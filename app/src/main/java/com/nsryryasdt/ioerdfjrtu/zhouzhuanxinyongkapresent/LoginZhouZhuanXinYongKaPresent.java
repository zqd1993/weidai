package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkapresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.BaseRespModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.CompanyInfoZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.LoginRespZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.LoginStatusModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.HomePageZhouZhuanXinYongKaActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.LoginZhouZhuanXinYongKaActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StaticZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.ToastZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkawidget.CountDownTimerUtilsZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.mvp.XPresent;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.NetError;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.XApi;
import com.nsryryasdt.ioerdfjrtu.router.Router;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ApiSubscriber;

public class LoginZhouZhuanXinYongKaPresent extends XPresent<LoginZhouZhuanXinYongKaActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanXinYongKaApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelZhouZhuanXinYongKa>getApiTransformer())
                    .compose(XApi.<LoginStatusModelZhouZhuanXinYongKa>getScheduler())
                    .compose(getV().<LoginStatusModelZhouZhuanXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelZhouZhuanXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticZhouZhuanXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelZhouZhuanXinYongKa loginStatusModelZhouZhuanXinYongKa) {
                            if (loginStatusModelZhouZhuanXinYongKa != null) {
                                if ("1".equals(loginStatusModelZhouZhuanXinYongKa.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelZhouZhuanXinYongKa.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelZhouZhuanXinYongKa.getIs_code_register());
                                Log.d("zqd", "loginStatusModelZhouZhuanXinYongKa.getIs_agree_check() = " + loginStatusModelZhouZhuanXinYongKa.getIs_agree_check() +
                                        "--->loginStatusModelZhouZhuanXinYongKa.getIs_code_register() = " + loginStatusModelZhouZhuanXinYongKa.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanXinYongKaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>getScheduler())
                    .compose(getV().<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticZhouZhuanXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanXinYongKaApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>getScheduler())
                    .compose(getV().<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticZhouZhuanXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelZhouZhuanXinYongKa<LoginRespZhouZhuanXinYongKaModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageZhouZhuanXinYongKaActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastZhouZhuanXinYongKaUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanXinYongKaApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa>getApiTransformer())
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa>getScheduler())
                    .compose(getV().<BaseRespModelZhouZhuanXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticZhouZhuanXinYongKaUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelZhouZhuanXinYongKa gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastZhouZhuanXinYongKaUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsZhouZhuanXinYongKa mCountDownTimerUtilsZhouZhuanXinYongKa = new CountDownTimerUtilsZhouZhuanXinYongKa(textView, 60000, 1000);
                                    mCountDownTimerUtilsZhouZhuanXinYongKa.start();
                                }
                            }
                        }
                    });
        }
    }

}
