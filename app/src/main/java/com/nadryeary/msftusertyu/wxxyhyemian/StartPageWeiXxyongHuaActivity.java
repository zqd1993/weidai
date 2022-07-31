package com.nadryeary.msftusertyu.wxxyhyemian;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.nadryeary.msftusertyu.R;
import com.nadryeary.msftusertyu.wxxyhjiekou.WeiXxyongHuaApi;
import com.nadryeary.msftusertyu.wxxyhgongju.OpenWeiXxyongHuaUtil;
import com.nadryeary.msftusertyu.wxxyhgongju.PreferencesWeiXxyongHuaOpenUtil;
import com.nadryeary.msftusertyu.wxxyhgongju.StatusBarWeiXxyongHuaUtil;
import com.nadryeary.msftusertyu.wxxyhkongjian.RemindWeiXxyongHuaDialog;
import com.nadryeary.msftusertyu.wxxyhkongjian.StartPageWeiXxyongHuaRemindDialog;
import com.nadryeary.msftusertyu.mvp.XActivity;
import com.nadryeary.msftusertyu.net.ApiSubscriber;
import com.nadryeary.msftusertyu.net.NetError;
import com.nadryeary.msftusertyu.net.XApi;
import com.nadryeary.msftusertyu.wxxyhshiti.BaseWeiXxyongHuaModel;
import com.nadryeary.msftusertyu.wxxyhshiti.ConfigWeiXxyongHuaEntity;

import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartPageWeiXxyongHuaActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageWeiXxyongHuaRemindDialog startPageRemindDialog;

    private RemindWeiXxyongHuaDialog dialog;

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} back from opaque to
     * translucent following a call to
     * {@link #convertActivityFromTranslucent(android.app.Activity)} .
     * <p>
     * Calling this allows the Activity behind this one to be seen again. Once
     * all such Activities have been redrawn
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity);
        } else {
        }
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
            }
        }, 500);
    }

    private void showDialog() {
//        Looper.prepare();
        startPageRemindDialog = new StartPageWeiXxyongHuaRemindDialog(this);
        startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    StartPageWeiXxyongHuaActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialog.setOnListener(new StartPageWeiXxyongHuaRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
                initUm();
                PreferencesWeiXxyongHuaOpenUtil.saveBool("isSure", true);
                startPageRemindDialog.dismiss();
//                getValue(1);
                OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, DlWeiXxyongHuaActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", WeiXxyongHuaApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.yryvb));
                OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, JumpH5WeiXxyongHuaActivity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                startPageRemindDialog.dismiss();
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", WeiXxyongHuaApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.retert));
                OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, JumpH5WeiXxyongHuaActivity.class, bundle);
            }
        });
        startPageRemindDialog.show();
//        Looper.loop();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://ossbj0714.oss-cn-beijing.aliyuncs.com/server7701.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                PreferencesWeiXxyongHuaOpenUtil.saveString("API_BASE_URL", "http://" + net[0]);
                                PreferencesWeiXxyongHuaOpenUtil.saveString("AGREEMENT", net[1]);
                                Thread.sleep(1000);
                                jumpPage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jumpPage() {
        if (isSure) {
            initUm();
            if (TextUtils.isEmpty(phone)) {
//                getValue(1);
                OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, DlWeiXxyongHuaActivity.class, null, true);
            } else {
//                getValue(2);
                OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, MainWeiXxyongHuaActivity.class, null, true);
            }
        } else {
            showDialog();
        }
    }


    /**
     * Calling the convertToTranslucent method on platforms after Android 5.0
     */
    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable t) {
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
//        if (!UMConfigure.isInit) {
//            UMConfigure.setLogEnabled(true);
//            Log.d("youmeng", "zhuche chenggong");
//            //友盟正式初始化
////            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
//            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
//            // 参数一：当前上下文context；
//            // 参数二：应用申请的Appkey（需替换）；
//            // 参数三：渠道名称；
//            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
//            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
//            UMConfigure.init(this, "6270c126d024421570db03fa", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        }
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable t) {
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarWeiXxyongHuaUtil.setTransparent(this, false);
        isSure = PreferencesWeiXxyongHuaOpenUtil.getBool("isSure");
        phone = PreferencesWeiXxyongHuaOpenUtil.getString("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
        dialog = new RemindWeiXxyongHuaDialog(this).setCancelText("退出")
                .setConfirmText("确认").setTitle("温馨提示").setContent("当前手机没有SIM卡，禁止使用当前应用！");
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    StartPageWeiXxyongHuaActivity.this.finish();
                    return false;
                }
                return true;
            }

        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnButtonClickListener(new RemindWeiXxyongHuaDialog.OnButtonClickListener() {
            @Override
            public void onSureClicked() {
                finish();
                dialog.dismiss();
            }

            @Override
            public void onCancelClicked() {
                finish();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.wxxyh_activity_start_page;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getValue(int type) {
        WeiXxyongHuaApi.getInterfaceUtils().getValue("APP_SM")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel<ConfigWeiXxyongHuaEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel<ConfigWeiXxyongHuaEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData().getAppSm().equals("1")) {
                                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                                if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {
                                    dialog.show();
                                    return;
                                }
                            }
                            switch (type) {
                                case 1:
                                    OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, DlWeiXxyongHuaActivity.class, null, true);
                                    break;
                                case 2:
                                    OpenWeiXxyongHuaUtil.getValue(StartPageWeiXxyongHuaActivity.this, MainWeiXxyongHuaActivity.class, null, true);
                                    break;
                            }
                        }
                    }
                });
    }
}
