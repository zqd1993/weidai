package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StatusBarUtilJieDianQian;

import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.JieDianQianApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianwidget.WelcomeDialogJieDianQian;
//import com.umeng.commonsdk.UMConfigure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeJieDianQianActivity extends XActivity {

    private WelcomeDialogJieDianQian welcomeDialogJieDianQian;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        welcomeDialogJieDianQian = new WelcomeDialogJieDianQian(this, "温馨提示");
        welcomeDialogJieDianQian.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeJieDianQianActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialogJieDianQian.setOnClickedListener(new WelcomeDialogJieDianQian.OnClickedListener() {
            @Override
            public void topBtnClicked() {
//                initUm();
                JieDianQianSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                JieDianQianSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                welcomeDialogJieDianQian.dismiss();
                StaticJieDianQianUtil.getValue(WelcomeJieDianQianActivity.this, JieDianQianLoginActivity.class, null, true);
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeJieDianQianActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JieDianQianApi.PRIVACY_POLICY);
                StaticJieDianQianUtil.getValue(WelcomeJieDianQianActivity.this, WebViewActivityJieDianQian.class, bundle);
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JieDianQianApi.USER_SERVICE_AGREEMENT);
                StaticJieDianQianUtil.getValue(WelcomeJieDianQianActivity.this, WebViewActivityJieDianQian.class, bundle);
            }
        });
        welcomeDialogJieDianQian.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7733.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                JieDianQianSharedPreferencesUtilis.saveStringIntoPref("HTTP_API_URL", "http://" + net[0]);
                                JieDianQianSharedPreferencesUtilis.saveStringIntoPref("AGREEMENT", net[1]);
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
//            initUm();
            if (!TextUtils.isEmpty(loginPhone)) {
                StaticJieDianQianUtil.getValue(WelcomeJieDianQianActivity.this, HomePageJieDianQianActivity.class, null, true);
            } else {
                StaticJieDianQianUtil.getValue(WelcomeJieDianQianActivity.this, JieDianQianLoginActivity.class, null, true);
            }
        } else {
            showDialog();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialogJieDianQian != null) {
            welcomeDialogJieDianQian.dismiss();
            welcomeDialogJieDianQian = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilJieDianQian.setTransparent(this, false);
        isAgree = JieDianQianSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = JieDianQianSharedPreferencesUtilis.getStringFromPref("phone");
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
        return R.layout.activity_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }

//    private void initUm() {
//        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
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
//            UMConfigure.init(this, "628d034188ccdf4b7e76524e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        }
//    }
}
