package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss;

import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.LoginStatusModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.widgetqueqianmaboss.WelcomeDialogQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiSubscriber;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.NetError;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.XApi;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StatusBarQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.router.Router;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeQueQianMaBossActivity extends XActivity {

    private WelcomeDialogQueQianMaBoss welcomeDialogQueQianMaBoss;

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
        welcomeDialogQueQianMaBoss = new WelcomeDialogQueQianMaBoss(this, "温馨提示");
        welcomeDialogQueQianMaBoss.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeQueQianMaBossActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeDialogQueQianMaBoss.setOnClickedListener(new WelcomeDialogQueQianMaBoss.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilisQueQianMaBoss.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeQueQianMaBossActivity.this)
                        .to(LoginQueQianMaBossActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeQueQianMaBossActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiQueQianMaBoss.getZc());
                Router.newIntent(WelcomeQueQianMaBossActivity.this)
                        .to(QueQianMaBossWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiQueQianMaBoss.getYs());
                Router.newIntent(WelcomeQueQianMaBossActivity.this)
                        .to(QueQianMaBossWebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialogQueQianMaBoss.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://wentree.oss-cn-hangzhou.aliyuncs.com/oqqm.json")
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
//                Router.newIntent(WelcomeQueQianMaBossActivity.this)
//                        .to(QueQianMaBossWebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                ApiQueQianMaBoss.API_BASE_URL = net;
                SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("API_BASE_URL", net);
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
                Router.newIntent(WelcomeQueQianMaBossActivity.this)
                        .to(HomePageActivityQueQianMaBoss.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeQueQianMaBossActivity.this)
                        .to(LoginQueQianMaBossActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            ApiQueQianMaBoss.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelQueQianMaBoss>getApiTransformer())
                    .compose(XApi.<LoginStatusModelQueQianMaBoss>getScheduler())
                    .compose(this.<LoginStatusModelQueQianMaBoss>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelQueQianMaBoss>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusModelQueQianMaBoss loginStatusModelQueQianMaBoss) {
                            if (loginStatusModelQueQianMaBoss != null) {
                                SharedPreferencesUtilisQueQianMaBoss.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusModelQueQianMaBoss.getIs_agree_check()));
                                SharedPreferencesUtilisQueQianMaBoss.saveBoolIntoPref("is_code_register", "0".equals(loginStatusModelQueQianMaBoss.getIs_code_register()));
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
        if (welcomeDialogQueQianMaBoss != null) {
            welcomeDialogQueQianMaBoss.dismiss();
            welcomeDialogQueQianMaBoss = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarQueQianMaBossUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilisQueQianMaBoss.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weclome_que_qian_ma_boss;
    }

    @Override
    public Object newP() {
        return null;
    }
}
