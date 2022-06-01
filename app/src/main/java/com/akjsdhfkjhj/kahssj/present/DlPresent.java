package com.akjsdhfkjhj.kahssj.present;

import android.view.View;
import android.widget.TextView;

import com.akjsdhfkjhj.kahssj.model.BaseModel;
import com.akjsdhfkjhj.kahssj.model.PeiZhiModel;
import com.akjsdhfkjhj.kahssj.model.LoginModel;
import com.akjsdhfkjhj.kahssj.ui.MainActivity;
import com.akjsdhfkjhj.kahssj.ui.LoginActivityHuiMin;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.MainUtil;
import com.akjsdhfkjhj.kahssj.utils.ToastUtil;
import com.akjsdhfkjhj.kahssj.widget.CDTimerUtils;
import com.akjsdhfkjhj.kahssj.mvp.XPresent;
import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.net.NetError;
import com.akjsdhfkjhj.kahssj.net.XApi;
import com.akjsdhfkjhj.kahssj.router.Router;
import com.akjsdhfkjhj.kahssj.net.ApiSubscriber;

public class DlPresent extends XPresent<LoginActivityHuiMin> {

    public void login(String phone, String verificationStr, String ip) {

        Api.getGankService().login(phone, verificationStr, "", ip)
                .compose(XApi.<BaseModel<LoginModel>>getApiTransformer())
                .compose(XApi.<BaseModel<LoginModel>>getScheduler())
                .compose(getV().<BaseModel<LoginModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<LoginModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        MainUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseModel<LoginModel> gankResults) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        if (gankResults != null && gankResults.getCode() == 200) {
                            if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                SPUtilis.saveStringIntoPref("phone", phone);
                                SPUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                SPUtilis.saveStringIntoPref("ip", ip);
                                Router.newIntent(getV())
                                        .to(MainActivity.class)
                                        .launch();
                                getV().finish();
                            }
                        } else {
                            if (gankResults.getCode() == 500){
                                ToastUtil.showShort(gankResults.getMsg());
                            }
                        }
                    }
                });
    }

    public void getGankData() {
        Api.getGankService().getGankData()
                .compose(XApi.<BaseModel<PeiZhiModel>>getApiTransformer())
                .compose(XApi.<BaseModel<PeiZhiModel>>getScheduler())
                .compose(getV().<BaseModel<PeiZhiModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<PeiZhiModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        MainUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseModel<PeiZhiModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                SPUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
                                if ("0".equals(gankResults.getData().getIsCodeLogin())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "1".equals(gankResults.getData().getIsSelectLogin());
                                getV().isNeedVerification = "1".equals(gankResults.getData().getIsCodeLogin());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    }
                });
    }

    public void sendVerifyCode(String phone, TextView textView) {
        Api.getGankService().sendVerifyCode(phone)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        MainUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseModel gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200) {
                                ToastUtil.showShort("验证码发送成功");
                                CDTimerUtils mCountDownTimerUtils = new CDTimerUtils(textView, 60000, 1000);
                                mCountDownTimerUtils.start();
                            }
                        }
                    }
                });
    }

}
