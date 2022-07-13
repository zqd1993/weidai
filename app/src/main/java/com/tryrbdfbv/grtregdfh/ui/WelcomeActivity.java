package com.tryrbdfbv.grtregdfh.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.model.LoginStatusModel;
import com.tryrbdfbv.grtregdfh.mvp.XActivity;
import com.tryrbdfbv.grtregdfh.net.ApiSubscriber;
import com.tryrbdfbv.grtregdfh.net.NetError;
import com.tryrbdfbv.grtregdfh.net.XApi;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilis;
import com.tryrbdfbv.grtregdfh.utils.StaticUtil;
import com.tryrbdfbv.grtregdfh.utils.StatusBarUtil;
import com.tryrbdfbv.grtregdfh.router.Router;

import com.tryrbdfbv.grtregdfh.net.Api;
import com.tryrbdfbv.grtregdfh.widget.WelcomeDialog;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivity extends XActivity {

    private WelcomeDialog welcomeDialog;

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
        welcomeDialog = new WelcomeDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeActivity.this)
                        .to(LoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.getZc());
                Router.newIntent(WelcomeActivity.this)
                        .to(WebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.getYs());
                Router.newIntent(WelcomeActivity.this)
                        .to(WebViewActivity.class)
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
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/k-xygj.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            String net = jsonObject.getString("data");//取得其名字的值，一般是字符串
            if (!TextUtils.isEmpty(net)) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("tag", 1);
//                bundle.putString("url", net);
//                Router.newIntent(WelcomeActivity.this)
//                        .to(WebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                Api.API_BASE_URL = net;
                SharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
                Thread.sleep(1000);
                getGankData();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jumpPage() {
        if (isAgree) {
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(WelcomeActivity.this)
                        .to(HomePageActivity.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeActivity.this)
                        .to(LoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModel>getApiTransformer())
                    .compose(XApi.<LoginStatusModel>getScheduler())
                    .compose(this.<LoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusModel loginStatusModel) {
                            if (loginStatusModel != null) {
                                SharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusModel.getIs_agree_check()));
                                SharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginStatusModel.getIs_code_register()));
                                jumpPage();
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialog != null) {
            welcomeDialog.dismiss();
            welcomeDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
