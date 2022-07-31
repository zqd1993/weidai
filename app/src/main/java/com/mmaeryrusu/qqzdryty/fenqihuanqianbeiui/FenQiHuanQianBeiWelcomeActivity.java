package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.LoginStatusFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusFenQiHuanQianBeiBarUtil;
import com.mmaeryrusu.qqzdryty.router.Router;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.WelcomeFenQiHuanQianBeiDialog;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FenQiHuanQianBeiWelcomeActivity extends XActivity {

    private WelcomeFenQiHuanQianBeiDialog welcomeFenQiHuanQianBeiDialog;

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
        welcomeFenQiHuanQianBeiDialog = new WelcomeFenQiHuanQianBeiDialog(this, "温馨提示");
        welcomeFenQiHuanQianBeiDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    FenQiHuanQianBeiWelcomeActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeFenQiHuanQianBeiDialog.setOnClickedListener(new WelcomeFenQiHuanQianBeiDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                FenQiHuanQianBeiSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                FenQiHuanQianBeiSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(FenQiHuanQianBeiWelcomeActivity.this)
                        .to(LoginFenQiHuanQianBeiActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                FenQiHuanQianBeiWelcomeActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiFenQiHuanQianBei.getZc());
                Router.newIntent(FenQiHuanQianBeiWelcomeActivity.this)
                        .to(WebViewActivityFenQiHuanQianBei.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiFenQiHuanQianBei.getYs());
                Router.newIntent(FenQiHuanQianBeiWelcomeActivity.this)
                        .to(WebViewActivityFenQiHuanQianBei.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeFenQiHuanQianBeiDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/729/ofqhqb.json")
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
//                Router.newIntent(FenQiHuanQianBeiWelcomeActivity.this)
//                        .to(WebViewActivityFenQiHuanQianBei.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                ApiFenQiHuanQianBei.API_BASE_URL = net;
                FenQiHuanQianBeiSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(FenQiHuanQianBeiWelcomeActivity.this)
                        .to(HomePageFenQiHuanQianBeiActivity.class)
                        .launch();
            } else {
                Router.newIntent(FenQiHuanQianBeiWelcomeActivity.this)
                        .to(LoginFenQiHuanQianBeiActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiFenQiHuanQianBei.getGankService().getGankData()
                    .compose(XApi.<LoginStatusFenQiHuanQianBeiModel>getApiTransformer())
                    .compose(XApi.<LoginStatusFenQiHuanQianBeiModel>getScheduler())
                    .compose(this.<LoginStatusFenQiHuanQianBeiModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusFenQiHuanQianBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusFenQiHuanQianBeiModel loginStatusFenQiHuanQianBeiModel) {
                            if (loginStatusFenQiHuanQianBeiModel != null) {
                                FenQiHuanQianBeiSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusFenQiHuanQianBeiModel.getIs_agree_check()));
                                FenQiHuanQianBeiSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginStatusFenQiHuanQianBeiModel.getIs_code_register()));
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
        if (welcomeFenQiHuanQianBeiDialog != null) {
            welcomeFenQiHuanQianBeiDialog.dismiss();
            welcomeFenQiHuanQianBeiDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusFenQiHuanQianBeiBarUtil.setTransparent(this, false);
        isAgree = FenQiHuanQianBeiSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_qi_huan_qian_bei_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
