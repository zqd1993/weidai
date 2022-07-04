package com.rtyhdfh.mnzdfgdsg.uuuu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.rtyhdfh.mnzdfgdsg.R;
import com.rtyhdfh.mnzdfgdsg.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.StatusGeiNiHuaBarUtil;
import com.rtyhdfh.mnzdfgdsg.router.Router;

import com.rtyhdfh.mnzdfgdsg.nnnn.ApiGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.wwww.WelcomeDialogGeiNiHua;
import com.umeng.commonsdk.UMConfigure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeGeiNiHuaActivity extends AppCompatActivity {

    private WelcomeDialogGeiNiHua welcomeDialogGeiNiHua;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geinihua_weclome);
        StatusGeiNiHuaBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisGeiNiHua.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisGeiNiHua.getStringFromPref("phone");
        sendRequestWithOkHttp();
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

    public static String iutydyhh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ndzgdr(Object o) {

        return toDouble(o, 0);
    }

    public static double retfgs(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long mgfhfxgh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void showDialog() {
        Looper.prepare();
        welcomeDialogGeiNiHua = new WelcomeDialogGeiNiHua(this, "温馨提示");
        welcomeDialogGeiNiHua.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeGeiNiHuaActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialogGeiNiHua.setOnClickedListener(new WelcomeDialogGeiNiHua.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisGeiNiHua.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeGeiNiHuaActivity.this)
                        .to(LoginGeiNiHuaActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeGeiNiHuaActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiGeiNiHua.PRIVACY_POLICY);
                Router.newIntent(WelcomeGeiNiHuaActivity.this)
                        .to(GeiNiHuaWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiGeiNiHua.USER_SERVICE_AGREEMENT);
                Router.newIntent(WelcomeGeiNiHuaActivity.this)
                        .to(GeiNiHuaWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogGeiNiHua.show();
        Looper.loop();
    }

    public static String zvzdfgh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double piuljkl(Object o) {

        return toDouble(o, 0);
    }

    public static double ewrfgsdg(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long mxbxfh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7736.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
//                        HttpApi.HTTP_API_URL = "http://" + responseData;
                        SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("HTTP_API_URL", "http://" + responseData);
                        Thread.sleep(1000);
                        jumpPage();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jumpPage() {
        if (isAgree) {
            initUm();
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(WelcomeGeiNiHuaActivity.this)
                        .to(HomePageActivityGeiNiHua.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeGeiNiHuaActivity.this)
                        .to(LoginGeiNiHuaActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public static String poiuikujk(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double bfxhfyh(Object o) {

        return toDouble(o, 0);
    }

    public static double wergfdsg(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long nmgxdfrty(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialogGeiNiHua != null){
            welcomeDialogGeiNiHua.dismiss();
            welcomeDialogGeiNiHua = null;
        }
        super.onDestroy();
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (!UMConfigure.isInit) {
            UMConfigure.setLogEnabled(true);
            Log.d("youmeng", "zhuche chenggong");
            //友盟正式初始化
//            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
            UMConfigure.init(this, "62c0072388ccdf4b7eb8a60b", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    public static String vcztgrt(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ljhjgfj(Object o) {

        return toDouble(o, 0);
    }

    public static double ergfsdg(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long mxfghryh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }
}
