package com.wolai.dai.yemian;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wolai.dai.R;
import com.wolai.dai.jiekou.JiXinApi;
import com.wolai.dai.gongju.JiXinOpenUtil;
import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;
import com.wolai.dai.gongju.JiXinStatusBarUtil;
import com.wolai.dai.kongjian.JixinStartPageRemindDialog;
import com.wolai.dai.mvp.XActivity;

import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JixinStartPageActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private JixinStartPageRemindDialog startPageRemindDialog;

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
        startPageRemindDialog = new JixinStartPageRemindDialog(this);
        startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    JixinStartPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialog.setOnListener(new JixinStartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
                JiXinPreferencesOpenUtil.saveBool("isSure", true);
                startPageRemindDialog.dismiss();
                JiXinOpenUtil.getValue(JixinStartPageActivity.this, JixinDlActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", JiXinApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.yryvb));
                JiXinOpenUtil.getValue(JixinStartPageActivity.this, JixinJumpH5Activity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", JiXinApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.retert));
                JiXinOpenUtil.getValue(JixinStartPageActivity.this, JixinJumpH5Activity.class, bundle);
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
                                JiXinPreferencesOpenUtil.saveString("API_BASE_URL", "http://" + net[0]);
                                JiXinPreferencesOpenUtil.saveString("AGREEMENT", net[1]);
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
            if (TextUtils.isEmpty(phone)) {
                JiXinOpenUtil.getValue(JixinStartPageActivity.this, JixinDlActivity.class, null, true);
            } else {
                JiXinOpenUtil.getValue(JixinStartPageActivity.this, JixinMainActivity.class, null, true);
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
        JiXinStatusBarUtil.setTransparent(this, false);
        isSure = JiXinPreferencesOpenUtil.getBool("isSure");
        phone = JiXinPreferencesOpenUtil.getString("phone");
        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jixin_activity_start_page;
    }

    @Override
    public Object newP() {
        return null;
    }
}
