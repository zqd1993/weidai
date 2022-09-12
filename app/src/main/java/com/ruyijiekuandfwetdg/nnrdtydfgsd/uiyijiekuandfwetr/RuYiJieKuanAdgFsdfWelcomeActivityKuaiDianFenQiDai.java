package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginStatusModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XActivity;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.RuYiJieKuanAdgFsdfApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.ApiSubscriber;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetError;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.XApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStatusBarUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.router.Router;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.widgetyijiekuandfwetr.WelcomeDialogRuYiJieKuanAdgFsdf;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai extends XActivity {

    private WelcomeDialogRuYiJieKuanAdgFsdf welcomeDialogRuYiJieKuanAdgFsdf;

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
        welcomeDialogRuYiJieKuanAdgFsdf = new WelcomeDialogRuYiJieKuanAdgFsdf(this, "温馨提示");
        welcomeDialogRuYiJieKuanAdgFsdf.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogRuYiJieKuanAdgFsdf.setOnClickedListener(new WelcomeDialogRuYiJieKuanAdgFsdf.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this)
                        .to(RuYiJieKuanAdgFsdfLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", RuYiJieKuanAdgFsdfApi.getZc());
                Router.newIntent(RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this)
                        .to(WebViewActivityRuYiJieKuanAdgFsdf.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", RuYiJieKuanAdgFsdfApi.getYs());
                Router.newIntent(RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this)
                        .to(WebViewActivityRuYiJieKuanAdgFsdf.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogRuYiJieKuanAdgFsdf.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://wentree.oss-cn-hangzhou.aliyuncs.com/95/vryjk.json")
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
//                        .to(WebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                RuYiJieKuanAdgFsdfApi.API_BASE_URL = net;
                RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this)
                        .to(HomePageActivityRuYiJieKuanAdgFsdf.class)
                        .launch();
            } else {
                Router.newIntent(RuYiJieKuanAdgFsdfWelcomeActivityKuaiDianFenQiDai.this)
                        .to(RuYiJieKuanAdgFsdfLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RuYiJieKuanAdgFsdfApi.getGankService().getGankData()
                    .compose(XApi.<RuYiJieKuanAdgFsdfLoginStatusModel>getApiTransformer())
                    .compose(XApi.<RuYiJieKuanAdgFsdfLoginStatusModel>getScheduler())
                    .compose(this.<RuYiJieKuanAdgFsdfLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<RuYiJieKuanAdgFsdfLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(RuYiJieKuanAdgFsdfLoginStatusModel ruYiJieKuanAdgFsdfLoginStatusModel) {
                            if (ruYiJieKuanAdgFsdfLoginStatusModel != null) {
                                RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(ruYiJieKuanAdgFsdfLoginStatusModel.getIs_agree_check()));
                                RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(ruYiJieKuanAdgFsdfLoginStatusModel.getIs_code_register()));
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
        if (welcomeDialogRuYiJieKuanAdgFsdf != null) {
            welcomeDialogRuYiJieKuanAdgFsdf.dismiss();
            welcomeDialogRuYiJieKuanAdgFsdf = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        RuYiJieKuanAdgFsdfStatusBarUtil.setTransparent(this, false);
        isAgree = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public Object newP() {
        return null;
    }
}
