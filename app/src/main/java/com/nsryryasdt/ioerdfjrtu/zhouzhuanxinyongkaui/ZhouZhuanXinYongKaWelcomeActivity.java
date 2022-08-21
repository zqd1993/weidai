package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.LoginStatusModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.mvp.XActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ApiSubscriber;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.NetError;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.XApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StatusBarUtilZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.router.Router;

import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkawidget.WelcomeZhouZhuanXinYongKaDialog;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ZhouZhuanXinYongKaWelcomeActivity extends XActivity {

    private WelcomeZhouZhuanXinYongKaDialog welcomeZhouZhuanXinYongKaDialog;

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
        welcomeZhouZhuanXinYongKaDialog = new WelcomeZhouZhuanXinYongKaDialog(this, "温馨提示");
        welcomeZhouZhuanXinYongKaDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    ZhouZhuanXinYongKaWelcomeActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeZhouZhuanXinYongKaDialog.setOnClickedListener(new WelcomeZhouZhuanXinYongKaDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisZhouZhuanXinYongKa.saveBoolIntoPref("agree", true);
                Router.newIntent(ZhouZhuanXinYongKaWelcomeActivity.this)
                        .to(LoginZhouZhuanXinYongKaActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                ZhouZhuanXinYongKaWelcomeActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ZhouZhuanXinYongKaApi.getZc());
                Router.newIntent(ZhouZhuanXinYongKaWelcomeActivity.this)
                        .to(WebViewActivityZhouZhuanXinYongKa.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ZhouZhuanXinYongKaApi.getYs());
                Router.newIntent(ZhouZhuanXinYongKaWelcomeActivity.this)
                        .to(WebViewActivityZhouZhuanXinYongKa.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeZhouZhuanXinYongKaDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/724/u-ygzqb.json")
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
//                Router.newIntent(ZhouZhuanXinYongKaWelcomeActivity.this)
//                        .to(WebViewActivityZhouZhuanXinYongKa.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                ZhouZhuanXinYongKaApi.API_BASE_URL = net;
                SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(ZhouZhuanXinYongKaWelcomeActivity.this)
                        .to(HomePageZhouZhuanXinYongKaActivity.class)
                        .launch();
            } else {
                Router.newIntent(ZhouZhuanXinYongKaWelcomeActivity.this)
                        .to(LoginZhouZhuanXinYongKaActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanXinYongKaApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelZhouZhuanXinYongKa>getApiTransformer())
                    .compose(XApi.<LoginStatusModelZhouZhuanXinYongKa>getScheduler())
                    .compose(this.<LoginStatusModelZhouZhuanXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelZhouZhuanXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusModelZhouZhuanXinYongKa loginStatusModelZhouZhuanXinYongKa) {
                            if (loginStatusModelZhouZhuanXinYongKa != null) {
                                SharedPreferencesUtilisZhouZhuanXinYongKa.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusModelZhouZhuanXinYongKa.getIs_agree_check()));
                                SharedPreferencesUtilisZhouZhuanXinYongKa.saveBoolIntoPref("is_code_register", "0".equals(loginStatusModelZhouZhuanXinYongKa.getIs_code_register()));
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
        if (welcomeZhouZhuanXinYongKaDialog != null) {
            welcomeZhouZhuanXinYongKaDialog.dismiss();
            welcomeZhouZhuanXinYongKaDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilZhouZhuanXinYongKa.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisZhouZhuanXinYongKa.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }
}
