package com.jinyu.xiaopu.fenfujieui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.mvp.XActivity;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.fenfujieutils.FenFuJieStatusBarUtil;

import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujiewidget.WelcomeFenFuJieDialog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeFenFuJieActivity extends XActivity {

    private WelcomeFenFuJieDialog welcomeFenFuJieDialog;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

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
        welcomeFenFuJieDialog = new WelcomeFenFuJieDialog(this, "温馨提示");
        welcomeFenFuJieDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeFenFuJieActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeFenFuJieDialog.setOnClickedListener(new WelcomeFenFuJieDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                SharedPreferencesUtilisFenFuJie.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisFenFuJie.saveBoolIntoPref("agree", true);
                welcomeFenFuJieDialog.dismiss();
                StaticFenFuJieUtil.getValue(WelcomeFenFuJieActivity.this, LoginFenFuJieActivity.class, null, true);
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeFenFuJieActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", FenFuJieApi.PRIVACY_POLICY);
                StaticFenFuJieUtil.getValue(WelcomeFenFuJieActivity.this, WebViewActivityFenFuJie.class, bundle);
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", FenFuJieApi.USER_SERVICE_AGREEMENT);
                StaticFenFuJieUtil.getValue(WelcomeFenFuJieActivity.this, WebViewActivityFenFuJie.class, bundle);
            }
        });
        welcomeFenFuJieDialog.show();
//        Looper.loop();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7702.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                SharedPreferencesUtilisFenFuJie.saveStringIntoPref("HTTP_API_URL", "http://" + net[0]);
                                SharedPreferencesUtilisFenFuJie.saveStringIntoPref("AGREEMENT", net[1]);
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
            initUm();
            if (!TextUtils.isEmpty(loginPhone)) {
                StaticFenFuJieUtil.getValue(WelcomeFenFuJieActivity.this, HomePageFenFuJieActivity.class, null, true);
            } else {
                StaticFenFuJieUtil.getValue(WelcomeFenFuJieActivity.this, LoginFenFuJieActivity.class, null, true);
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
        if (welcomeFenFuJieDialog != null) {
            welcomeFenFuJieDialog.dismiss();
            welcomeFenFuJieDialog = null;
        }
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
//            UMConfigure.init(this, "628d034188ccdf4b7e76524e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        FenFuJieStatusBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisFenFuJie.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisFenFuJie.getStringFromPref("phone");
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
        return R.layout.activity_fen_fu_jie_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
