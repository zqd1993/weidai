package com.tysffgh.wfdgdfg.doudoupresent;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tysffgh.wfdgdfg.doudoumodel.BaseRespModelDouDou;
import com.tysffgh.wfdgdfg.doudoumodel.CompanDouDouyInfoModel;
import com.tysffgh.wfdgdfg.doudoumodel.LoginRespDouDouModel;
import com.tysffgh.wfdgdfg.doudoumodel.DouDouLoginStatusModel;
import com.tysffgh.wfdgdfg.doudouui.DouDouLoginActivity;
import com.tysffgh.wfdgdfg.doudouui.HomePageDouDouActivity;
import com.tysffgh.wfdgdfg.doudouutils.SharedDouDouPreferencesUtilis;
import com.tysffgh.wfdgdfg.doudouutils.StaticUtilDouDou;
import com.tysffgh.wfdgdfg.doudouutils.ToastUtilDouDou;
import com.tysffgh.wfdgdfg.doudouwidget.CountDownTimerUtilsDouDou;
import com.tysffgh.wfdgdfg.mvp.XPresent;
import com.tysffgh.wfdgdfg.doudounet.ApiDouDou;
import com.tysffgh.wfdgdfg.doudounet.NetError;
import com.tysffgh.wfdgdfg.doudounet.XApi;
import com.tysffgh.wfdgdfg.router.Router;
import com.tysffgh.wfdgdfg.doudounet.ApiSubscriber;

import java.lang.reflect.Method;
import java.util.List;

public class LoginDouDouPresent extends XPresent<DouDouLoginActivity> {

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiDouDou.getGankService().getGankData()
                    .compose(XApi.<DouDouLoginStatusModel>getApiTransformer())
                    .compose(XApi.<DouDouLoginStatusModel>getScheduler())
                    .compose(getV().<DouDouLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<DouDouLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilDouDou.showError(getV(), error);
                        }

                        @Override
                        public void onNext(DouDouLoginStatusModel douDouLoginStatusModel) {
                            if (douDouLoginStatusModel != null) {
                                if ("1".equals(douDouLoginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(douDouLoginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(douDouLoginStatusModel.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + douDouLoginStatusModel.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + douDouLoginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String getCurrentProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String getCurrentProcessNameByActivityThread() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String getCurrentProcessNameByActivityManager(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiDouDou.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelDouDou<CompanDouDouyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDouDou<CompanDouDouyInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelDouDou<CompanDouDouyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDouDou<CompanDouDouyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilDouDou.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDouDou<CompanDouDouyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedDouDouPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String ityfgh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String ndfyherty() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String wertgfs(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiDouDou.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelDouDou<LoginRespDouDouModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDouDou<LoginRespDouDouModel>>getScheduler())
                    .compose(getV().<BaseRespModelDouDou<LoginRespDouDouModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDouDou<LoginRespDouDouModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilDouDou.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDouDou<LoginRespDouDouModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedDouDouPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    SharedDouDouPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageDouDouActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilDouDou.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String mdfhewrt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String utergdfhgn() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String rethgsy(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiDouDou.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelDouDou>getApiTransformer())
                    .compose(XApi.<BaseRespModelDouDou>getScheduler())
                    .compose(getV().<BaseRespModelDouDou>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDouDou>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilDouDou.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDouDou gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilDouDou.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsDouDou mCountDownTimerUtils = new CountDownTimerUtilsDouDou(textView, 60000, 1000);
                                    mCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String ngfzrtgf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String ghdrete() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String fddfzyrety(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

}
