package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.LoginDiXiDaiKuanStatusModel;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.ApiSubscriber;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.DiXiDaiKuanApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetError;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.XApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanStatusBarUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.router.Router;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanwidget.WelcomeDialogDiXiDaiKuan;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeDiXiDaiKuanActivity extends XActivity {

    private WelcomeDialogDiXiDaiKuan welcomeDialogDiXiDaiKuan;

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
        welcomeDialogDiXiDaiKuan = new WelcomeDialogDiXiDaiKuan(this, "温馨提示");
        welcomeDialogDiXiDaiKuan.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeDiXiDaiKuanActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogDiXiDaiKuan.setOnClickedListener(new WelcomeDialogDiXiDaiKuan.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedDiXiDaiKuanPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeDiXiDaiKuanActivity.this)
                        .to(DiXiDaiKuanLoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeDiXiDaiKuanActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", DiXiDaiKuanApi.getZc());
                Router.newIntent(WelcomeDiXiDaiKuanActivity.this)
                        .to(WebViewActivityDiXiDaiKuan.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", DiXiDaiKuanApi.getYs());
                Router.newIntent(WelcomeDiXiDaiKuanActivity.this)
                        .to(WebViewActivityDiXiDaiKuan.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogDiXiDaiKuan.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://amiancp.oss-cn-hangzhou.aliyuncs.com/vdxdk.json")
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
                DiXiDaiKuanApi.API_BASE_URL = net;
                SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeDiXiDaiKuanActivity.this)
                        .to(HomePageDiXiDaiKuanActivity.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeDiXiDaiKuanActivity.this)
                        .to(DiXiDaiKuanLoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DiXiDaiKuanApi.getGankService().getGankData()
                    .compose(XApi.<LoginDiXiDaiKuanStatusModel>getApiTransformer())
                    .compose(XApi.<LoginDiXiDaiKuanStatusModel>getScheduler())
                    .compose(this.<LoginDiXiDaiKuanStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginDiXiDaiKuanStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginDiXiDaiKuanStatusModel loginDiXiDaiKuanStatusModel) {
                            if (loginDiXiDaiKuanStatusModel != null) {
                                SharedDiXiDaiKuanPreferencesUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginDiXiDaiKuanStatusModel.getIs_agree_check()));
                                SharedDiXiDaiKuanPreferencesUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginDiXiDaiKuanStatusModel.getIs_code_register()));
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
        if (welcomeDialogDiXiDaiKuan != null) {
            welcomeDialogDiXiDaiKuan.dismiss();
            welcomeDialogDiXiDaiKuan = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        DiXiDaiKuanStatusBarUtil.setTransparent(this, false);
        isAgree = SharedDiXiDaiKuanPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_di_xi_dai_kuan_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
