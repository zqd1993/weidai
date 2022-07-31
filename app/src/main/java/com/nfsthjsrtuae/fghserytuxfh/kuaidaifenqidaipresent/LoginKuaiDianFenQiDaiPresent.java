package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaipresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.BaseRespModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.CompanyKuaiDianFenQiDaiInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.LoginKuaiDianFenQiDaiRespModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.LoginKuaiDianFenQiDaiStatusModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.HomePageActivityKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.LoginKuaiDianFenQiDaiActivity;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.KuaiDianFenQiDaiStaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.ToastUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiwidget.CountDownKuaiDianFenQiDaiTimerUtils;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XPresent;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiSubscriber;

public class LoginKuaiDianFenQiDaiPresent extends XPresent<LoginKuaiDianFenQiDaiActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiKuaiDianFenQiDai.getGankService().getGankData()
                    .compose(XApi.<LoginKuaiDianFenQiDaiStatusModel>getApiTransformer())
                    .compose(XApi.<LoginKuaiDianFenQiDaiStatusModel>getScheduler())
                    .compose(getV().<LoginKuaiDianFenQiDaiStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginKuaiDianFenQiDaiStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginKuaiDianFenQiDaiStatusModel loginKuaiDianFenQiDaiStatusModel) {
                            if (loginKuaiDianFenQiDaiStatusModel != null) {
                                if ("1".equals(loginKuaiDianFenQiDaiStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginKuaiDianFenQiDaiStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginKuaiDianFenQiDaiStatusModel.getIs_code_register());
                                Log.d("zqd", "loginKuaiDianFenQiDaiStatusModel.getIs_agree_check() = " + loginKuaiDianFenQiDaiStatusModel.getIs_agree_check() +
                                        "--->loginKuaiDianFenQiDaiStatusModel.getIs_code_register() = " + loginKuaiDianFenQiDaiStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiKuaiDianFenQiDai.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiKuaiDianFenQiDai.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKuaiDianFenQiDai<LoginKuaiDianFenQiDaiRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageActivityKuaiDianFenQiDai.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilKuaiDianFenQiDai.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiKuaiDianFenQiDai.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai>getApiTransformer())
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai>getScheduler())
                    .compose(getV().<BaseRespModelKuaiDianFenQiDai>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai>() {
                        @Override
                        protected void onFail(NetError error) {
                            KuaiDianFenQiDaiStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKuaiDianFenQiDai gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilKuaiDianFenQiDai.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownKuaiDianFenQiDaiTimerUtils mCountDownKuaiDianFenQiDaiTimerUtils = new CountDownKuaiDianFenQiDaiTimerUtils(textView, 60000, 1000);
                                    mCountDownKuaiDianFenQiDaiTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
