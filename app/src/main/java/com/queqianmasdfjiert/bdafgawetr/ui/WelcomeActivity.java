package com.queqianmasdfjiert.bdafgawetr.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.model.LoginStatusModel;
import com.queqianmasdfjiert.bdafgawetr.net.Api;
import com.queqianmasdfjiert.bdafgawetr.utils.SharedPreferencesUtilis;
import com.queqianmasdfjiert.bdafgawetr.widget.WelcomeDialog;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;
import com.queqianmasdfjiert.bdafgawetr.net.ApiSubscriber;
import com.queqianmasdfjiert.bdafgawetr.net.NetError;
import com.queqianmasdfjiert.bdafgawetr.net.XApi;
import com.queqianmasdfjiert.bdafgawetr.utils.StatusBarUtil;
import com.queqianmasdfjiert.bdafgawetr.router.Router;

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
                            .url("https://wentree.oss-cn-hangzhou.aliyuncs.com/oqqm.json")
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
