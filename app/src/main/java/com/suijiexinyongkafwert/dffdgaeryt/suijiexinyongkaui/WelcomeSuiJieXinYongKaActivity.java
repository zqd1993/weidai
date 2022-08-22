package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.LoginStatusModelSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XActivity;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.ApiSubscriber;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.NetError;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.XApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StatusBarUtilSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.router.Router;

import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.SuiJieXinYongKaApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkawidget.WelcomeSuiJieXinYongKaDialog;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeSuiJieXinYongKaActivity extends XActivity {

    private WelcomeSuiJieXinYongKaDialog welcomeSuiJieXinYongKaDialog;

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
        welcomeSuiJieXinYongKaDialog = new WelcomeSuiJieXinYongKaDialog(this, "温馨提示");
        welcomeSuiJieXinYongKaDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeSuiJieXinYongKaActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeSuiJieXinYongKaDialog.setOnClickedListener(new WelcomeSuiJieXinYongKaDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SuiJieXinYongKaSharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeSuiJieXinYongKaActivity.this)
                        .to(LoginSuiJieXinYongKaActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeSuiJieXinYongKaActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", SuiJieXinYongKaApi.getZc());
                Router.newIntent(WelcomeSuiJieXinYongKaActivity.this)
                        .to(WebViewSuiJieXinYongKaActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", SuiJieXinYongKaApi.getYs());
                Router.newIntent(WelcomeSuiJieXinYongKaActivity.this)
                        .to(WebViewSuiJieXinYongKaActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeSuiJieXinYongKaDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/724/u-qqm.json")
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
//                Router.newIntent(WelcomeActivity.this)
//                        .to(WebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                SuiJieXinYongKaApi.API_BASE_URL = net;
                SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeSuiJieXinYongKaActivity.this)
                        .to(HomePageActivitySuiJieXinYongKa.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeSuiJieXinYongKaActivity.this)
                        .to(LoginSuiJieXinYongKaActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            SuiJieXinYongKaApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelSuiJieXinYongKa>getApiTransformer())
                    .compose(XApi.<LoginStatusModelSuiJieXinYongKa>getScheduler())
                    .compose(this.<LoginStatusModelSuiJieXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelSuiJieXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusModelSuiJieXinYongKa loginStatusModelSuiJieXinYongKa) {
                            if (loginStatusModelSuiJieXinYongKa != null) {
                                SuiJieXinYongKaSharedPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusModelSuiJieXinYongKa.getIs_agree_check()));
                                SuiJieXinYongKaSharedPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginStatusModelSuiJieXinYongKa.getIs_code_register()));
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
        if (welcomeSuiJieXinYongKaDialog != null) {
            welcomeSuiJieXinYongKaDialog.dismiss();
            welcomeSuiJieXinYongKaDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilSuiJieXinYongKa.setTransparent(this, false);
        isAgree = SuiJieXinYongKaSharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_sui_jie_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }
}
