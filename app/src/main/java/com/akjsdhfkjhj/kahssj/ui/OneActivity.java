package com.akjsdhfkjhj.kahssj.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.StatusBarUtil;
import com.akjsdhfkjhj.kahssj.router.Router;

import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.widget.OneDialog;
import com.umeng.commonsdk.UMConfigure;

public class OneActivity extends AppCompatActivity {

    private OneDialog welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setTransparent(this, false);
        isAgree = SPUtilis.getBoolFromPref("agree");
        loginPhone = SPUtilis.getStringFromPref("phone");
        if (!isAgree) {
            showDialog();
        } else {
            initUm();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(loginPhone)) {
                        Router.newIntent(OneActivity.this)
                                .to(MainActivity.class)
                                .launch();
                    } else {
                        Router.newIntent(OneActivity.this)
                                .to(LoginActivityHuiMin.class)
                                .launch();
                    }
                    finish();
                }
            }, 1000);
        }
    }

    private void showDialog() {
        welcomeDialog = new OneDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    OneActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new OneDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                SPUtilis.saveStringIntoPref("uminit", "1");
                SPUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(OneActivity.this)
                        .to(LoginActivityHuiMin.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                OneActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.PRIVACY_POLICY);
                Router.newIntent(OneActivity.this)
                        .to(WebActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                Router.newIntent(OneActivity.this)
                        .to(WebActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialog.show();
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

    public String getOrderCommodityId(Object entity) {
        if (entity == null) {
            return null;
        }
        return "";
    }

    private void initUm(){
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        UMConfigure.preInit(getApplicationContext(), "6277e4e4d024421570e6ea0c", "Umeng");
        /**
         * 打开app首次隐私协议授权，以及sdk初始化，判断逻辑请查看SplashTestActivity
         */
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (SPUtilis.getStringFromPref("uminit").equals("1")) {
            //友盟正式初始化
//            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
            UMConfigure.init(this, "6277e4e4d024421570e6ea0c", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }
}
