package com.aklsfasad.fsjhfkk.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.StatusBarUtilHuiMin;
import com.aklsfasad.fsjhfkk.router.Router;

import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.widget.WelcomeDialogHuiMin;
import com.umeng.commonsdk.UMConfigure;

public class TanPingActivity extends XActivity {

    private WelcomeDialogHuiMin welcomeDialog;

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
        welcomeDialog = new WelcomeDialogHuiMin(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    TanPingActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeDialogHuiMin.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                SharedPreferencesUtilisHuiMin.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisHuiMin.saveBoolIntoPref("agree", true);
                welcomeDialog.dismiss();
                StaticUtilHuiMin.getValue(TanPingActivity.this, LoginActivityHuiMin.class, null, true);
            }

            @Override
            public void bottomBtnClicked() {
                TanPingActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.PRIVACY_POLICY);
                StaticUtilHuiMin.getValue(TanPingActivity.this, WebHuiMinActivity.class, bundle);
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                StaticUtilHuiMin.getValue(TanPingActivity.this, WebHuiMinActivity.class, bundle);
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
            UMConfigure.init(this, "6294d48405844627b599751c", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilHuiMin.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisHuiMin.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisHuiMin.getStringFromPref("phone");
        if (!isAgree) {
            showDialog();
        } else {
            initUm();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(loginPhone)) {
                        StaticUtilHuiMin.getValue(TanPingActivity.this, HomePageActivityHuiMin.class, null, true);
                    } else {
                        StaticUtilHuiMin.getValue(TanPingActivity.this, LoginActivityHuiMin.class, null, true);
                    }
                }
            }, 1000);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public Object newP() {
        return null;
    }
}
