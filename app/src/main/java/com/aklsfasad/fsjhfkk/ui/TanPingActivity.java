package com.aklsfasad.fsjhfkk.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StatusBarUtilHuiMin;
import com.aklsfasad.fsjhfkk.router.Router;

import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.widget.WelcomeDialogHuiMin;

public class TanPingActivity extends AppCompatActivity {

    private WelcomeDialogHuiMin welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtilHuiMin.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisHuiMin.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisHuiMin.getStringFromPref("phone");
        if (!isAgree) {
            showDialog();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(loginPhone)) {
                        Router.newIntent(TanPingActivity.this)
                                .to(HomePageActivityHuiMin.class)
                                .launch();
                    } else {
                        Router.newIntent(TanPingActivity.this)
                                .to(LoginActivityHuiMin.class)
                                .launch();
                    }
                    finish();
                }
            }, 1000);
        }
    }

    private void showDialog() {
        welcomeDialog = new WelcomeDialogHuiMin(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    TanPingActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeDialogHuiMin.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesUtilisHuiMin.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisHuiMin.saveBoolIntoPref("agree", true);
                Router.newIntent(TanPingActivity.this)
                        .to(LoginActivityHuiMin.class)
                        .launch();
                finish();
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
                Router.newIntent(TanPingActivity.this)
                        .to(WebHuiMinActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                Router.newIntent(TanPingActivity.this)
                        .to(WebHuiMinActivity.class)
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
