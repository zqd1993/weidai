package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.LoginKuaiDianFenQiDaiStatusModel;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.StatusBarUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiwidget.WelcomeDialogKuaiDianFenQiDai;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivityKuaiDianFenQiDai extends XActivity {

    private WelcomeDialogKuaiDianFenQiDai welcomeDialogKuaiDianFenQiDai;

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
        welcomeDialogKuaiDianFenQiDai = new WelcomeDialogKuaiDianFenQiDai(this, "温馨提示");
        welcomeDialogKuaiDianFenQiDai.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeActivityKuaiDianFenQiDai.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogKuaiDianFenQiDai.setOnClickedListener(new WelcomeDialogKuaiDianFenQiDai.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesKuaiDianFenQiDaiUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(LoginKuaiDianFenQiDaiActivity.class)
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
                bundle.putString("url", ApiKuaiDianFenQiDai.getZc());
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(KuaiDianFenQiDaiWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiKuaiDianFenQiDai.getYs());
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(KuaiDianFenQiDaiWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogKuaiDianFenQiDai.show();
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
                ApiKuaiDianFenQiDai.API_BASE_URL = net;
                SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                        .to(HomePageActivityKuaiDianFenQiDai.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeActivityKuaiDianFenQiDai.this)
                        .to(LoginKuaiDianFenQiDaiActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiKuaiDianFenQiDai.getGankService().getGankData()
                    .compose(XApi.<LoginKuaiDianFenQiDaiStatusModel>getApiTransformer())
                    .compose(XApi.<LoginKuaiDianFenQiDaiStatusModel>getScheduler())
                    .compose(this.<LoginKuaiDianFenQiDaiStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginKuaiDianFenQiDaiStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginKuaiDianFenQiDaiStatusModel loginKuaiDianFenQiDaiStatusModel) {
                            if (loginKuaiDianFenQiDaiStatusModel != null) {
                                SharedPreferencesKuaiDianFenQiDaiUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginKuaiDianFenQiDaiStatusModel.getIs_agree_check()));
                                SharedPreferencesKuaiDianFenQiDaiUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginKuaiDianFenQiDaiStatusModel.getIs_code_register()));
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
        if (welcomeDialogKuaiDianFenQiDai != null) {
            welcomeDialogKuaiDianFenQiDai.dismiss();
            welcomeDialogKuaiDianFenQiDai = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilKuaiDianFenQiDai.setTransparent(this, false);
        isAgree = SharedPreferencesKuaiDianFenQiDaiUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_kuai_dian_fen_qi_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
