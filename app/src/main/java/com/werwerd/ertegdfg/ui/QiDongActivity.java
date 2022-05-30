package com.werwerd.ertegdfg.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StatusBarYouXinUtil;
import com.werwerd.ertegdfg.router.Router;

import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.widget.WelcomeYouXinDialog;

public class QiDongActivity extends AppCompatActivity {

    private WelcomeYouXinDialog welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarYouXinUtil.setTransparent(this, false);
        isAgree = SharedPreferencesYouXinUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesYouXinUtilis.getStringFromPref("phone");
        if (!isAgree) {
            showDialog();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
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
                }
            }, 1000);
        }
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

    private void showDialog() {
        welcomeDialog = new WelcomeYouXinDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    QiDongActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeYouXinDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
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

    public String getOrderCommodityId(Object entity) {
        if (entity == null) {
            return null;
        }
        return "";
    }
}
