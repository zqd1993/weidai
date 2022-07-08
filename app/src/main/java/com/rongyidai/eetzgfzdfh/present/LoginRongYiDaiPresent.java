package com.rongyidai.eetzgfzdfh.present;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.OverScroller;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.rongyidai.eetzgfzdfh.model.BaseRespModelRongYiDai;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiCompanyInfoModel;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiLoginRespModel;
import com.rongyidai.eetzgfzdfh.model.LoginRongYiDaiStatusModel;
import com.rongyidai.eetzgfzdfh.ui.LoginActivityRongYiDai;
import com.rongyidai.eetzgfzdfh.ui.RongYiDaiHomePageActivity;
import com.rongyidai.eetzgfzdfh.utils.SharedRongYiDaiPreferencesUtilis;
import com.rongyidai.eetzgfzdfh.utils.StaticUtilRongYiDaiRongYiDai;
import com.rongyidai.eetzgfzdfh.utils.RongYiDaiToastUtil;
import com.rongyidai.eetzgfzdfh.widget.RongYiDaiCountDownTimerUtils;
import com.rongyidai.eetzgfzdfh.mvp.XPresent;
import com.rongyidai.eetzgfzdfh.net.RongYiDaiApi;
import com.rongyidai.eetzgfzdfh.net.NetError;
import com.rongyidai.eetzgfzdfh.net.XApi;
import com.rongyidai.eetzgfzdfh.router.Router;
import com.rongyidai.eetzgfzdfh.net.ApiSubscriber;

import java.lang.reflect.Field;

public class LoginRongYiDaiPresent extends XPresent<LoginActivityRongYiDai> {

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field nfgjtyitdu() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field retyyrhxfh() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void werfdfhfu(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedRongYiDaiPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RongYiDaiApi.getGankService().getGankData()
                    .compose(XApi.<LoginRongYiDaiStatusModel>getApiTransformer())
                    .compose(XApi.<LoginRongYiDaiStatusModel>getScheduler())
                    .compose(getV().<LoginRongYiDaiStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginRongYiDaiStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilRongYiDaiRongYiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginRongYiDaiStatusModel loginRongYiDaiStatusModel) {
                            if (loginRongYiDaiStatusModel != null) {
                                if ("1".equals(loginRongYiDaiStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginRongYiDaiStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginRongYiDaiStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginRongYiDaiStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginRongYiDaiStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field bxfdhdrtur() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field fdgdxfhrtuy() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void oidthfxhgzer(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedRongYiDaiPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RongYiDaiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilRongYiDaiRongYiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedRongYiDaiPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedRongYiDaiPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RongYiDaiApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelRongYiDai<RongYiDaiLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRongYiDai<RongYiDaiLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelRongYiDai<RongYiDaiLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRongYiDai<RongYiDaiLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilRongYiDaiRongYiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRongYiDai<RongYiDaiLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedRongYiDaiPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedRongYiDaiPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(RongYiDaiHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    RongYiDaiToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field zgdxfrtu() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field jhzdgrty() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void fdgdxfhtru(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedRongYiDaiPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RongYiDaiApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelRongYiDai>getApiTransformer())
                    .compose(XApi.<BaseRespModelRongYiDai>getScheduler())
                    .compose(getV().<BaseRespModelRongYiDai>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRongYiDai>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilRongYiDaiRongYiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRongYiDai gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                RongYiDaiToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    RongYiDaiCountDownTimerUtils mRongYiDaiCountDownTimerUtils = new RongYiDaiCountDownTimerUtils(textView, 60000, 1000);
                                    mRongYiDaiCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field getFlingRunnableField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field getScrollerField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void stopAppbarLayoutFling(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
