package com.chenqi.lecheng.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.utils.SharedPreferencesYouXinUtilis;
import com.chenqi.lecheng.utils.StatusBarYouXinUtil;
import com.chenqi.lecheng.router.Router;

import com.chenqi.lecheng.net.Api;
import com.chenqi.lecheng.widget.WelcomeYouXinDialog;
import com.umeng.commonsdk.UMConfigure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QiDongActivity extends AppCompatActivity {

    private WelcomeYouXinDialog welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarYouXinUtil.setTransparent(this, false);
        isAgree = SharedPreferencesYouXinUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesYouXinUtilis.getStringFromPref("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialog != null){
            welcomeDialog.dismiss();
            welcomeDialog = null;
        }
        super.onDestroy();
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
        welcomeDialog = new WelcomeYouXinDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    QiDongActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeYouXinDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                SharedPreferencesYouXinUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesYouXinUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(QiDongActivity.this)
                        .to(LoginYouXinActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                QiDongActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.PRIVACY_POLICY);
                Router.newIntent(QiDongActivity.this)
                        .to(WebActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                Router.newIntent(QiDongActivity.this)
                        .to(WebActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7706.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        Api.API_BASE_URL = "http://" + responseData;
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
                Router.newIntent(QiDongActivity.this)
                        .to(HomePageYouXinActivity.class)
                        .launch();
            } else {
                Router.newIntent(QiDongActivity.this)
                        .to(LoginYouXinActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
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
            UMConfigure.init(this, "626681afd024421570cf064d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    public String getOrderCommodityId(Object entity) {
        if (entity == null) {
            return null;
        }
        return "";
    }
}
