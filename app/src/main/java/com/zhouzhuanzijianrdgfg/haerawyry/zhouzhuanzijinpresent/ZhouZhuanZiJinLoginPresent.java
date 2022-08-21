package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinpresent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinCompanyInfoModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginRespModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinLoginStatusModel;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.ZhouZhuanZiJinHomePageActivity;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.ZhouZhuanZiJinLoginActivity;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinStaticUtil;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinutils.ZhouZhuanZiJinToastUtil;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinwidget.CountDownTimerUtils;
import com.zhouzhuanzijianrdgfg.haerawyry.mvp.XPresent;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.NetError;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.XApi;
import com.zhouzhuanzijianrdgfg.haerawyry.router.Router;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinnet.ApiSubscriber;

public class ZhouZhuanZiJinLoginPresent extends XPresent<ZhouZhuanZiJinLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanZiJinApi.getGankService().getGankData()
                    .compose(XApi.<ZhouZhuanZiJinLoginStatusModel>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinLoginStatusModel>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            ZhouZhuanZiJinStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinLoginStatusModel zhouZhuanZiJinLoginStatusModel) {
                            if (zhouZhuanZiJinLoginStatusModel != null) {
                                if ("1".equals(zhouZhuanZiJinLoginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(zhouZhuanZiJinLoginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(zhouZhuanZiJinLoginStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + zhouZhuanZiJinLoginStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + zhouZhuanZiJinLoginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanZiJinApi.getGankService().getCompanyInfo()
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            ZhouZhuanZiJinStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    ZhouZhuanZiJinSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanZiJinApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            ZhouZhuanZiJinStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    ZhouZhuanZiJinSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    ZhouZhuanZiJinSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(ZhouZhuanZiJinHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ZhouZhuanZiJinToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanZiJinApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel>getScheduler())
                    .compose(getV().<ZhouZhuanZiJinBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            ZhouZhuanZiJinStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ZhouZhuanZiJinToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(textView, 60000, 1000);
                                    mCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

}
