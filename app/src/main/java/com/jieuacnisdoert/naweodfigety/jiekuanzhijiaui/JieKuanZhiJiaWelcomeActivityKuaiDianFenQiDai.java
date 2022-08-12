package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaLoginStatusModel;
import com.jieuacnisdoert.naweodfigety.mvp.XActivity;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.JieKuanZhiJiaApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.ApiSubscriber;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.NetError;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.XApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStatusBarUtil;
import com.jieuacnisdoert.naweodfigety.router.Router;

import com.jieuacnisdoert.naweodfigety.jiekuanzhijiawidget.WelcomeDialogJieKuanZhiJia;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai extends XActivity {

    private WelcomeDialogJieKuanZhiJia welcomeDialogJieKuanZhiJia;

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
        welcomeDialogJieKuanZhiJia = new WelcomeDialogJieKuanZhiJia(this, "温馨提示");
        welcomeDialogJieKuanZhiJia.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogJieKuanZhiJia.setOnClickedListener(new WelcomeDialogJieKuanZhiJia.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                JieKuanZhiJiaSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this)
                        .to(JieKuanZhiJiaLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", JieKuanZhiJiaApi.getZc());
                Router.newIntent(JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this)
                        .to(WebViewActivityJieKuanZhiJia.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", JieKuanZhiJiaApi.getYs());
                Router.newIntent(JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this)
                        .to(WebViewActivityJieKuanZhiJia.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogJieKuanZhiJia.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/812/ojkzj.json")
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
                JieKuanZhiJiaApi.API_BASE_URL = net;
                JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this)
                        .to(HomePageActivityJieKuanZhiJia.class)
                        .launch();
            } else {
                Router.newIntent(JieKuanZhiJiaWelcomeActivityKuaiDianFenQiDai.this)
                        .to(JieKuanZhiJiaLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().getGankData()
                    .compose(XApi.<JieKuanZhiJiaLoginStatusModel>getApiTransformer())
                    .compose(XApi.<JieKuanZhiJiaLoginStatusModel>getScheduler())
                    .compose(this.<JieKuanZhiJiaLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JieKuanZhiJiaLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(JieKuanZhiJiaLoginStatusModel jieKuanZhiJiaLoginStatusModel) {
                            if (jieKuanZhiJiaLoginStatusModel != null) {
                                JieKuanZhiJiaSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(jieKuanZhiJiaLoginStatusModel.getIs_agree_check()));
                                JieKuanZhiJiaSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(jieKuanZhiJiaLoginStatusModel.getIs_code_register()));
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
        if (welcomeDialogJieKuanZhiJia != null) {
            welcomeDialogJieKuanZhiJia.dismiss();
            welcomeDialogJieKuanZhiJia = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        JieKuanZhiJiaStatusBarUtil.setTransparent(this, false);
        isAgree = JieKuanZhiJiaSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_jie_kuan_zhi_jia;
    }

    @Override
    public Object newP() {
        return null;
    }
}
