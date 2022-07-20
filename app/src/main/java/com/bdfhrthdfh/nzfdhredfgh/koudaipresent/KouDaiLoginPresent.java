package com.bdfhrthdfh.nzfdhredfgh.koudaipresent;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.BaseRespModelKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.CompanyKouDaiInfoModel;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.LoginRespModelKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.LoginKouDaiStatusModel;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.KouDaiHomePageActivity;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.LoginKouDaiActivity;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.SharedPreferencesUtilisKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.StaticKouDaiUtil;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.ToastUtilKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiwidget.CountDownTimerUtilsKouDai;
import com.bdfhrthdfh.nzfdhredfgh.mvp.XPresent;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.KouDaiApi;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.NetError;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.XApi;
import com.bdfhrthdfh.nzfdhredfgh.router.Router;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.ApiSubscriber;

public class KouDaiLoginPresent extends XPresent<LoginKouDaiActivity> {

    private void resetViewRl(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            KouDaiApi.getGankService().getGankData()
                    .compose(XApi.<LoginKouDaiStatusModel>getApiTransformer())
                    .compose(XApi.<LoginKouDaiStatusModel>getScheduler())
                    .compose(getV().<LoginKouDaiStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginKouDaiStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticKouDaiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginKouDaiStatusModel loginKouDaiStatusModel) {
                            if (loginKouDaiStatusModel != null) {
                                if ("1".equals(loginKouDaiStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginKouDaiStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginKouDaiStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginKouDaiStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginKouDaiStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            KouDaiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelKouDai<CompanyKouDaiInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai<CompanyKouDaiInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelKouDai<CompanyKouDaiInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai<CompanyKouDaiInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticKouDaiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai<CompanyKouDaiInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesUtilisKouDai.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    private void wertdfhfxhj(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void mmtrydfhdhg(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            KouDaiApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelKouDai<LoginRespModelKouDai>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai<LoginRespModelKouDai>>getScheduler())
                    .compose(getV().<BaseRespModelKouDai<LoginRespModelKouDai>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai<LoginRespModelKouDai>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticKouDaiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai<LoginRespModelKouDai> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisKouDai.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisKouDai.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(KouDaiHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilKouDai.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    private void werdfghdxfh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void ertgxdbdrty(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            KouDaiApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelKouDai>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai>getScheduler())
                    .compose(getV().<BaseRespModelKouDai>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticKouDaiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilKouDai.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsKouDai mCountDownTimerUtilsKouDai = new CountDownTimerUtilsKouDai(textView, 60000, 1000);
                                    mCountDownTimerUtilsKouDai.start();
                                }
                            }
                        }
                    });
        }
    }

}
