package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.LoginXianjinChaoShiStatusModel;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.StatusBarUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget.WelcomeDialogXianjinChaoShi;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivityXianjinChaoShi extends XActivity {

    private WelcomeDialogXianjinChaoShi welcomeDialogXianjinChaoShi;

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
        welcomeDialogXianjinChaoShi = new WelcomeDialogXianjinChaoShi(this, "温馨提示");
        welcomeDialogXianjinChaoShi.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeActivityXianjinChaoShi.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogXianjinChaoShi.setOnClickedListener(new WelcomeDialogXianjinChaoShi.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesXianjinChaoShiUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeActivityXianjinChaoShi.this)
                        .to(LoginXianjinChaoShiActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeActivityXianjinChaoShi.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiXianjinChaoShi.getZc());
                Router.newIntent(WelcomeActivityXianjinChaoShi.this)
                        .to(UserWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiXianjinChaoShi.getYs());
                Router.newIntent(WelcomeActivityXianjinChaoShi.this)
                        .to(UserWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogXianjinChaoShi.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/729/okdfqd.json")
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
//                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
//                        .to(KuaiDianFenQiDaiWebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                ApiXianjinChaoShi.API_BASE_URL = net;
                SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeActivityXianjinChaoShi.this)
                        .to(HomePageActivityXianjinChaoShi.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeActivityXianjinChaoShi.this)
                        .to(LoginXianjinChaoShiActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiXianjinChaoShi.getGankService().getGankData()
                    .compose(XApi.<LoginXianjinChaoShiStatusModel>getApiTransformer())
                    .compose(XApi.<LoginXianjinChaoShiStatusModel>getScheduler())
                    .compose(this.<LoginXianjinChaoShiStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginXianjinChaoShiStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginXianjinChaoShiStatusModel loginXianjinChaoShiStatusModel) {
                            if (loginXianjinChaoShiStatusModel != null) {
                                SharedPreferencesXianjinChaoShiUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginXianjinChaoShiStatusModel.getIs_agree_check()));
                                SharedPreferencesXianjinChaoShiUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginXianjinChaoShiStatusModel.getIs_code_register()));
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
        if (welcomeDialogXianjinChaoShi != null) {
            welcomeDialogXianjinChaoShi.dismiss();
            welcomeDialogXianjinChaoShi = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilXianjinChaoShi.setTransparent(this, false);
        isAgree = SharedPreferencesXianjinChaoShiUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_xian_jin_chao_shi;
    }

    @Override
    public Object newP() {
        return null;
    }
}
