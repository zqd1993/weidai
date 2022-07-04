package com.tysffgh.wfdgdfg.doudoupresent;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.tysffgh.wfdgdfg.doudoumodel.BaseRespModelDouDou;
import com.tysffgh.wfdgdfg.doudoumodel.DouDouGoodsModel;
import com.tysffgh.wfdgdfg.doudoumodel.RequDouDouModel;
import com.tysffgh.wfdgdfg.mvp.XPresent;
import com.tysffgh.wfdgdfg.doudounet.ApiDouDou;
import com.tysffgh.wfdgdfg.doudounet.ApiSubscriber;
import com.tysffgh.wfdgdfg.doudounet.NetError;
import com.tysffgh.wfdgdfg.doudounet.XApi;
import com.tysffgh.wfdgdfg.doudouui.fragment.ProductFragmentDouDou;
import com.tysffgh.wfdgdfg.doudouutils.SharedDouDouPreferencesUtilis;
import com.tysffgh.wfdgdfg.doudouutils.StaticUtilDouDou;

import java.lang.reflect.Method;
import java.util.List;

import okhttp3.RequestBody;


public class DouDouProductPresent extends XPresent<ProductFragmentDouDou> {

    private int mobileType;

    private String phone, token;

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

    public void productList() {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedDouDouPreferencesUtilis.getStringFromPref("token");
            RequDouDouModel model = new RequDouDouModel();
            model.setToken(token);
            RequestBody body = StaticUtilDouDou.createBody(new Gson().toJson(model));
            ApiDouDou.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelDouDou<List<DouDouGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDouDou<List<DouDouGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelDouDou<List<DouDouGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDouDou<List<DouDouGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticUtilDouDou.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDouDou<List<DouDouGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String trydfhfgh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String mdgdgtrt() {
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
    private String ertfhsyrdt(@NonNull Context context) {
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

    public void productClick(DouDouGoodsModel model) {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SharedDouDouPreferencesUtilis.getStringFromPref("phone");
            ApiDouDou.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelDouDou>getApiTransformer())
                    .compose(XApi.<BaseRespModelDouDou>getScheduler())
                    .compose(getV().<BaseRespModelDouDou>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDouDou>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilDouDou.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDouDou gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String msrythgfh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String dtyjhgfn() {
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
    private String werfdhyrdt(@NonNull Context context) {
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
