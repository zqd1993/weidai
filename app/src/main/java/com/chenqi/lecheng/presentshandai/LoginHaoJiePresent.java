package com.chenqi.lecheng.presentshandai;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.shadnaimodel.BaseRespHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.ConfigHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.LoginRespHaoJieModel;
import com.chenqi.lecheng.shandaipage.activityshandai.HomePageHaoJieActivity;
import com.chenqi.lecheng.shandaipage.activityshandai.LoginHaoJieActivity;
import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.chenqi.lecheng.utilsshandai.StaticHaoJieUtil;
import com.chenqi.lecheng.utilsshandai.ToastHaoJieUtil;
import com.chenqi.lecheng.widgetshandai.CountDownTimerHaoJieUtils;
import com.chenqi.lecheng.mvp.XPresent;
import com.chenqi.lecheng.shadnaihttp.ApiHaoJie;
import com.chenqi.lecheng.shadnaihttp.NetError;
import com.chenqi.lecheng.shadnaihttp.XApi;
import com.chenqi.lecheng.router.Router;
import com.chenqi.lecheng.shadnaihttp.ApiSubscriber;
import com.google.android.material.snackbar.Snackbar;

public class LoginHaoJiePresent extends XPresent<LoginHaoJieActivity> {


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    public void dl(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiHaoJie.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespHaoJieModel<LoginRespHaoJieModel>>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel<LoginRespHaoJieModel>>getScheduler())
                    .compose(getV().<BaseRespHaoJieModel<LoginRespHaoJieModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel<LoginRespHaoJieModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticHaoJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel<LoginRespHaoJieModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesHaoJieUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesHaoJieUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesHaoJieUtilis.saveStringIntoPref("ip", ip);
                                    Router.newIntent(getV())
                                            .to(HomePageHaoJieActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastHaoJieUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void mjfgjg(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void wersdfsd(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void nbfgsdf(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiHaoJie.getGankService().getConfig()
                    .compose(XApi.<BaseRespHaoJieModel<ConfigHaoJieModel>>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel<ConfigHaoJieModel>>getScheduler())
                    .compose(getV().<BaseRespHaoJieModel<ConfigHaoJieModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel<ConfigHaoJieModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticHaoJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel<ConfigHaoJieModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesHaoJieUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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
    }

    public void getVerifyCode(String mobile, TextView tv) {
        if (!TextUtils.isEmpty(SharedPreferencesHaoJieUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiHaoJie.getGankService().sendVerifyCode(mobile)
                    .compose(XApi.<BaseRespHaoJieModel>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel>getScheduler())
                    .compose(getV().<BaseRespHaoJieModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticHaoJieUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastHaoJieUtil.showShort("验证码发送成功");
                                    CountDownTimerHaoJieUtils mCountDownTimerUtils = new CountDownTimerHaoJieUtils(tv, 60000, 1000);
                                    mCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void iyuurtghv(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void mdhsdfg(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void qwegdfat(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

}
