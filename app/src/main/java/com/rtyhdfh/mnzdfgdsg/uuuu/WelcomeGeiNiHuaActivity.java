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
import com.rtyhdfh.mnzdfgdsg.mvp.XActivity;
import com.rtyhdfh.mnzdfgdsg.utils.GeiNiHuaStaticUtil;
import com.rtyhdfh.mnzdfgdsg.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.StatusGeiNiHuaBarUtil;
import com.rtyhdfh.mnzdfgdsg.router.Router;

import com.rtyhdfh.mnzdfgdsg.nnnn.ApiGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.wwww.WelcomeDialogGeiNiHua;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeGeiNiHuaActivity extends XActivity {

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
//        Looper.prepare();
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
                SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisGeiNiHua.saveBoolIntoPref("agree", true);
                GeiNiHuaStaticUtil.getValue(WelcomeGeiNiHuaActivity.this, LoginGeiNiHuaActivity.class, null, true);
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
                GeiNiHuaStaticUtil.getValue(WelcomeGeiNiHuaActivity.this, GeiNiHuaWebViewActivity.class, bundle);
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiGeiNiHua.USER_SERVICE_AGREEMENT);
                GeiNiHuaStaticUtil.getValue(WelcomeGeiNiHuaActivity.this, GeiNiHuaWebViewActivity.class, bundle);
            }
        });
        welcomeDialogGeiNiHua.show();
//        Looper.loop();
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
                            .url("https://ossbj0714.oss-cn-beijing.aliyuncs.com/server7710.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("HTTP_API_URL", "http://" + net[0]);
                                SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("AGREEMENT", net[1]);
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
        if (isAgree) {
            if (!TextUtils.isEmpty(loginPhone)) {
                GeiNiHuaStaticUtil.getValue(WelcomeGeiNiHuaActivity.this, HomePageActivityGeiNiHua.class, null, true);
            } else {
                GeiNiHuaStaticUtil.getValue(WelcomeGeiNiHuaActivity.this, LoginGeiNiHuaActivity.class, null, true);
            }
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
        if (welcomeDialogGeiNiHua != null) {
            welcomeDialogGeiNiHua.dismiss();
            welcomeDialogGeiNiHua = null;
        }
        super.onDestroy();
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

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusGeiNiHuaBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisGeiNiHua.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisGeiNiHua.getStringFromPref("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_geinihua_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
