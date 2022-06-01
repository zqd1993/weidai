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
}
