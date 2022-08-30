package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.LoginStatusModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.YjjdFqjqApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqwidget.WelcomeYjjdFqjqDialog;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.ApiSubscriber;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.NetError;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.XApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqStatusBarUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.router.Router;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeYjjdFqjqActivity extends XActivity {

    private WelcomeYjjdFqjqDialog welcomeYjjdFqjqDialog;

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
        welcomeYjjdFqjqDialog = new WelcomeYjjdFqjqDialog(this, "温馨提示");
        welcomeYjjdFqjqDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeYjjdFqjqActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeYjjdFqjqDialog.setOnClickedListener(new WelcomeYjjdFqjqDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                YjjdFqjqSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeYjjdFqjqActivity.this)
                        .to(LoginYjjdFqjqActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeYjjdFqjqActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", YjjdFqjqApi.getZc());
                Router.newIntent(WelcomeYjjdFqjqActivity.this)
                        .to(YjjdFqjqWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", YjjdFqjqApi.getYs());
                Router.newIntent(WelcomeYjjdFqjqActivity.this)
                        .to(YjjdFqjqWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeYjjdFqjqDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://wentree.oss-cn-hangzhou.aliyuncs.com/oyjjdfqjq.json")
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
//                Router.newIntent(WelcomeYjjdFqjqActivity.this)
//                        .to(YjjdFqjqWebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                YjjdFqjqApi.API_BASE_URL = net;
                YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeYjjdFqjqActivity.this)
                        .to(HomePageActivityYjjdFqjq.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeYjjdFqjqActivity.this)
                        .to(LoginYjjdFqjqActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            YjjdFqjqApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelYjjdFqjq>getApiTransformer())
                    .compose(XApi.<LoginStatusModelYjjdFqjq>getScheduler())
                    .compose(this.<LoginStatusModelYjjdFqjq>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelYjjdFqjq>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusModelYjjdFqjq loginStatusModelYjjdFqjq) {
                            if (loginStatusModelYjjdFqjq != null) {
                                YjjdFqjqSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusModelYjjdFqjq.getIs_agree_check()));
                                YjjdFqjqSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginStatusModelYjjdFqjq.getIs_code_register()));
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
        if (welcomeYjjdFqjqDialog != null) {
            welcomeYjjdFqjqDialog.dismiss();
            welcomeYjjdFqjqDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        YjjdFqjqStatusBarUtil.setTransparent(this, false);
        isAgree = YjjdFqjqSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yjjdfqjq_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
