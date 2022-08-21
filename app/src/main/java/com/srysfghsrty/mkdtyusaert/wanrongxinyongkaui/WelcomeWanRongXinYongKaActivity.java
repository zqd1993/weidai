package com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.LoginStatusWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiSubscriber;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.NetError;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.XApi;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.StatusBarUtilWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.router.Router;

import com.srysfghsrty.mkdtyusaert.wanrongxinyongkawidget.WelcomeDialogWanRongXinYongKa;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeWanRongXinYongKaActivity extends XActivity {

    private WelcomeDialogWanRongXinYongKa welcomeDialogWanRongXinYongKa;

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
        welcomeDialogWanRongXinYongKa = new WelcomeDialogWanRongXinYongKa(this, "温馨提示");
        welcomeDialogWanRongXinYongKa.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeWanRongXinYongKaActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogWanRongXinYongKa.setOnClickedListener(new WelcomeDialogWanRongXinYongKa.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisWanRongXinYongKa.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeWanRongXinYongKaActivity.this)
                        .to(LoginWanRongXinYongKaActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeWanRongXinYongKaActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiWanRongXinYongKa.getZc());
                Router.newIntent(WelcomeWanRongXinYongKaActivity.this)
                        .to(WebViewWanRongXinYongKaActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiWanRongXinYongKa.getYs());
                Router.newIntent(WelcomeWanRongXinYongKaActivity.this)
                        .to(WebViewWanRongXinYongKaActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogWanRongXinYongKa.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/724/u-jyq.json")
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
//                Router.newIntent(WelcomeWanRongXinYongKaActivity.this)
//                        .to(WebViewWanRongXinYongKaActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                ApiWanRongXinYongKa.API_BASE_URL = net;
                SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeWanRongXinYongKaActivity.this)
                        .to(HomePageActivityWanRongXinYongKa.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeWanRongXinYongKaActivity.this)
                        .to(LoginWanRongXinYongKaActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ApiWanRongXinYongKa.getGankService().getGankData()
                    .compose(XApi.<LoginStatusWanRongXinYongKaModel>getApiTransformer())
                    .compose(XApi.<LoginStatusWanRongXinYongKaModel>getScheduler())
                    .compose(this.<LoginStatusWanRongXinYongKaModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusWanRongXinYongKaModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusWanRongXinYongKaModel loginStatusWanRongXinYongKaModel) {
                            if (loginStatusWanRongXinYongKaModel != null) {
                                SharedPreferencesUtilisWanRongXinYongKa.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusWanRongXinYongKaModel.getIs_agree_check()));
                                SharedPreferencesUtilisWanRongXinYongKa.saveBoolIntoPref("is_code_register", "0".equals(loginStatusWanRongXinYongKaModel.getIs_code_register()));
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
        if (welcomeDialogWanRongXinYongKa != null) {
            welcomeDialogWanRongXinYongKa.dismiss();
            welcomeDialogWanRongXinYongKa = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilWanRongXinYongKa.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisWanRongXinYongKa.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wan_rong_xin_yong_ka_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
