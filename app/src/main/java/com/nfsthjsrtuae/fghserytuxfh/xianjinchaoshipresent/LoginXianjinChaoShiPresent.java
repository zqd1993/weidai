package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshipresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.BaseRespModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.CompanyXianjinChaoShiInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.LoginXianjinChaoShiRespModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.LoginXianjinChaoShiStatusModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.HomePageActivityXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.LoginXianjinChaoShiActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.XianjinChaoShiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.ToastUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget.CountDowXianjinChaoShiTimerUtils;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiSubscriber;

public class LoginXianjinChaoShiPresent extends XPresent<LoginXianjinChaoShiActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiXianjinChaoShi.getGankService().getGankData()
                    .compose(XApi.<LoginXianjinChaoShiStatusModel>getApiTransformer())
                    .compose(XApi.<LoginXianjinChaoShiStatusModel>getScheduler())
                    .compose(getV().<LoginXianjinChaoShiStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginXianjinChaoShiStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            XianjinChaoShiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginXianjinChaoShiStatusModel loginXianjinChaoShiStatusModel) {
                            if (loginXianjinChaoShiStatusModel != null) {
                                if ("1".equals(loginXianjinChaoShiStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginXianjinChaoShiStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginXianjinChaoShiStatusModel.getIs_code_register());
                                Log.d("zqd", "loginKuaiDianFenQiDaiStatusModel.getIs_agree_check() = " + loginXianjinChaoShiStatusModel.getIs_agree_check() +
                                        "--->loginKuaiDianFenQiDaiStatusModel.getIs_code_register() = " + loginXianjinChaoShiStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiXianjinChaoShi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            XianjinChaoShiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiXianjinChaoShi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            XianjinChaoShiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianjinChaoShi<LoginXianjinChaoShiRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityXianjinChaoShi.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilXianjinChaoShi.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiXianjinChaoShi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelXianjinChaoShi>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianjinChaoShi>getScheduler())
                    .compose(getV().<BaseRespModelXianjinChaoShi>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi>() {
                        @Override
                        protected void onFail(NetError error) {
                            XianjinChaoShiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianjinChaoShi gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilXianjinChaoShi.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDowXianjinChaoShiTimerUtils mCountDowXianjinChaoShiTimerUtils = new CountDowXianjinChaoShiTimerUtils(textView, 60000, 1000);
                                    mCountDowXianjinChaoShiTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
