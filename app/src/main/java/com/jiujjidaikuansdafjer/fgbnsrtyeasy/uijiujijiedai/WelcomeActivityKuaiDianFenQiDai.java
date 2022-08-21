package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiLoginStatusModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.widgetjiujijiedai.JiuJiJieDaiWelcomeDialog;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.JiuJiJieDaiApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.ApiSubscriber;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.NetError;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.XApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiStatusBarUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.router.Router;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivityKuaiDianFenQiDai extends XActivity {

    private JiuJiJieDaiWelcomeDialog jiuJiJieDaiWelcomeDialog;

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
        jiuJiJieDaiWelcomeDialog = new JiuJiJieDaiWelcomeDialog(this, "温馨提示");
        jiuJiJieDaiWelcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeActivityKuaiDianFenQiDai.this.finish();
                    return true;
                }
                return false;
            }
        });
        jiuJiJieDaiWelcomeDialog.setOnClickedListener(new JiuJiJieDaiWelcomeDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                JiuJiJieDaiSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(JiuJiJieDaiLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeActivityKuaiDianFenQiDai.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JiuJiJieDaiApi.getZc());
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(JiuJiJieDaiWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JiuJiJieDaiApi.getYs());
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(JiuJiJieDaiWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        jiuJiJieDaiWelcomeDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/812/ojjjk.json")
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
                JiuJiJieDaiApi.API_BASE_URL = net;
                JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(JiuJiJieDaiHomePageActivity.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(JiuJiJieDaiLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieDaiApi.getGankService().getGankData()
                    .compose(XApi.<JiuJiJieDaiLoginStatusModel>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiLoginStatusModel>getScheduler())
                    .compose(this.<JiuJiJieDaiLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(JiuJiJieDaiLoginStatusModel jiuJiJieDaiLoginStatusModel) {
                            if (jiuJiJieDaiLoginStatusModel != null) {
                                JiuJiJieDaiSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(jiuJiJieDaiLoginStatusModel.getIs_agree_check()));
                                JiuJiJieDaiSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(jiuJiJieDaiLoginStatusModel.getIs_code_register()));
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
        if (jiuJiJieDaiWelcomeDialog != null) {
            jiuJiJieDaiWelcomeDialog.dismiss();
            jiuJiJieDaiWelcomeDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieDaiStatusBarUtil.setTransparent(this, false);
        isAgree = JiuJiJieDaiSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_jiu_ji_jie_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
