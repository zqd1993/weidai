package com.asdfgaery.nnaeryaery.daikuanqianbaoui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.LoginDaiKuanQianBaoStatusModel;
import com.asdfgaery.nnaeryaery.mvp.XActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.ApiSubscriber;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.DaiKuanQianBaoApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.NetError;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.XApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.DaiKuanQianBaoStatusBarUtil;
import com.asdfgaery.nnaeryaery.router.Router;

import com.asdfgaery.nnaeryaery.daikuanqianbaowidget.WelcomeDialogDaiKuanQianBao;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeDaiKuanQianBaoActivity extends XActivity {

    private WelcomeDialogDaiKuanQianBao welcomeDialogDaiKuanQianBao;

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
        welcomeDialogDaiKuanQianBao = new WelcomeDialogDaiKuanQianBao(this, "温馨提示");
        welcomeDialogDaiKuanQianBao.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeDaiKuanQianBaoActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogDaiKuanQianBao.setOnClickedListener(new WelcomeDialogDaiKuanQianBao.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedDaiKuanQianBaoPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeDaiKuanQianBaoActivity.this)
                        .to(DaiKuanQianBaoLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeDaiKuanQianBaoActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", DaiKuanQianBaoApi.getZc());
                Router.newIntent(WelcomeDaiKuanQianBaoActivity.this)
                        .to(WebViewActivityDaiKuanQianBao.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", DaiKuanQianBaoApi.getYs());
                Router.newIntent(WelcomeDaiKuanQianBaoActivity.this)
                        .to(WebViewActivityDaiKuanQianBao.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogDaiKuanQianBao.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/729/odxdk.json")
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
//                Router.newIntent(WelcomeDiXiDaiKuanActivity.this)
//                        .to(WebViewActivityDiXiDaiKuan.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                DaiKuanQianBaoApi.API_BASE_URL = net;
                SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeDaiKuanQianBaoActivity.this)
                        .to(HomePageDaiKuanQianBaoActivity.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeDaiKuanQianBaoActivity.this)
                        .to(DaiKuanQianBaoLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DaiKuanQianBaoApi.getGankService().getGankData()
                    .compose(XApi.<LoginDaiKuanQianBaoStatusModel>getApiTransformer())
                    .compose(XApi.<LoginDaiKuanQianBaoStatusModel>getScheduler())
                    .compose(this.<LoginDaiKuanQianBaoStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginDaiKuanQianBaoStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginDaiKuanQianBaoStatusModel loginDaiKuanQianBaoStatusModel) {
                            if (loginDaiKuanQianBaoStatusModel != null) {
                                SharedDaiKuanQianBaoPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginDaiKuanQianBaoStatusModel.getIs_agree_check()));
                                SharedDaiKuanQianBaoPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginDaiKuanQianBaoStatusModel.getIs_code_register()));
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
        if (welcomeDialogDaiKuanQianBao != null) {
            welcomeDialogDaiKuanQianBao.dismiss();
            welcomeDialogDaiKuanQianBao = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        DaiKuanQianBaoStatusBarUtil.setTransparent(this, false);
        isAgree = SharedDaiKuanQianBaoPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dai_kuan_qian_bao_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
