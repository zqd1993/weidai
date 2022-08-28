package com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.LoginJiuJiJieTiaojghsdfStatusModel;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.JiuJiJieTiaojghsdfApi;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaowidget.WelcomeDialogJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XActivity;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.ApiSubscriber;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.NetError;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.XApi;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfStatusBarUtil;
import com.jiujijietiaodsfwet.bsdwefhert.router.Router;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeJiuJiJieTiaojghsdfActivity extends XActivity {

    private WelcomeDialogJiuJiJieTiaojghsdf welcomeDialogJiuJiJieTiaojghsdf;

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
        welcomeDialogJiuJiJieTiaojghsdf = new WelcomeDialogJiuJiJieTiaojghsdf(this, "温馨提示");
        welcomeDialogJiuJiJieTiaojghsdf.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeJiuJiJieTiaojghsdfActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogJiuJiJieTiaojghsdf.setOnClickedListener(new WelcomeDialogJiuJiJieTiaojghsdf.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeJiuJiJieTiaojghsdfActivity.this)
                        .to(JiuJiJieTiaojghsdfLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeJiuJiJieTiaojghsdfActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JiuJiJieTiaojghsdfApi.getZc());
                Router.newIntent(WelcomeJiuJiJieTiaojghsdfActivity.this)
                        .to(WebViewActivityJiuJiJieTiaojghsdf.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JiuJiJieTiaojghsdfApi.getYs());
                Router.newIntent(WelcomeJiuJiJieTiaojghsdfActivity.this)
                        .to(WebViewActivityJiuJiJieTiaojghsdf.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogJiuJiJieTiaojghsdf.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://wentree.oss-cn-hangzhou.aliyuncs.com/ojjjt.json")
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
//                Router.newIntent(WelcomeJiuJiJieTiaojghsdfActivity.this)
//                        .to(WebViewActivityJiuJiJieTiaojghsdf.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                JiuJiJieTiaojghsdfApi.API_BASE_URL = net;
                SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeJiuJiJieTiaojghsdfActivity.this)
                        .to(HomePageJiuJiJieTiaojghsdfActivity.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeJiuJiJieTiaojghsdfActivity.this)
                        .to(JiuJiJieTiaojghsdfLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieTiaojghsdfApi.getGankService().getGankData()
                    .compose(XApi.<LoginJiuJiJieTiaojghsdfStatusModel>getApiTransformer())
                    .compose(XApi.<LoginJiuJiJieTiaojghsdfStatusModel>getScheduler())
                    .compose(this.<LoginJiuJiJieTiaojghsdfStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginJiuJiJieTiaojghsdfStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginJiuJiJieTiaojghsdfStatusModel loginJiuJiJieTiaojghsdfStatusModel) {
                            if (loginJiuJiJieTiaojghsdfStatusModel != null) {
                                SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginJiuJiJieTiaojghsdfStatusModel.getIs_agree_check()));
                                SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginJiuJiJieTiaojghsdfStatusModel.getIs_code_register()));
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
        if (welcomeDialogJiuJiJieTiaojghsdf != null) {
            welcomeDialogJiuJiJieTiaojghsdf.dismiss();
            welcomeDialogJiuJiJieTiaojghsdf = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieTiaojghsdfStatusBarUtil.setTransparent(this, false);
        isAgree = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiu_ji_jie_tiao_boss_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
