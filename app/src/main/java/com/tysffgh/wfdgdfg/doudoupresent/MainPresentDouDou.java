package com.tysffgh.wfdgdfg.doudoupresent;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import com.tysffgh.wfdgdfg.doudoumodel.BaseRespModelDouDou;
import com.tysffgh.wfdgdfg.doudoumodel.LoginRespDouDouModel;
import com.tysffgh.wfdgdfg.doudouui.HomePageDouDouActivity;
import com.tysffgh.wfdgdfg.doudouutils.SharedDouDouPreferencesUtilis;
import com.tysffgh.wfdgdfg.doudouutils.StaticUtilDouDou;
import com.tysffgh.wfdgdfg.mvp.XPresent;
import com.tysffgh.wfdgdfg.doudounet.ApiDouDou;
import com.tysffgh.wfdgdfg.doudounet.NetError;
import com.tysffgh.wfdgdfg.doudounet.XApi;
import com.tysffgh.wfdgdfg.doudounet.ApiSubscriber;

import java.lang.reflect.Method;
import java.util.List;

public class MainPresentDouDou extends XPresent<HomePageDouDouActivity> {

    private String phone, ip;

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

    public void login() {
        phone = SharedDouDouPreferencesUtilis.getStringFromPref("phone");
        ip = SharedDouDouPreferencesUtilis.getStringFromPref("ip");
        ApiDouDou.getGankService().logins(phone, ip)
                .compose(XApi.<BaseRespModelDouDou<LoginRespDouDouModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelDouDou<LoginRespDouDouModel>>getScheduler())
                .compose(getV().<BaseRespModelDouDou<LoginRespDouDouModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDouDou<LoginRespDouDouModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilDouDou.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDouDou<LoginRespDouDouModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String terfdgxfvb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String mnmsrtydfhg() {
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
    private String hfdffvxrty(@NonNull Context context) {
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
