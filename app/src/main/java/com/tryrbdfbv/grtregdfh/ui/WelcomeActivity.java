package com.tryrbdfbv.grtregdfh.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilis;
import com.tryrbdfbv.grtregdfh.utils.StatusBarUtil;
import com.tryrbdfbv.grtregdfh.router.Router;

import com.tryrbdfbv.grtregdfh.net.Api;
import com.tryrbdfbv.grtregdfh.widget.WelcomeDialog;
import com.umeng.commonsdk.UMConfigure;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {

    private WelcomeDialog welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weclome);
        StatusBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
        if (!isAgree) {
            showDialog();
        }
    }

    private void showDialog() {
        welcomeDialog = new WelcomeDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    WelcomeActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                SharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeActivity.this)
                        .to(LoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.getZc());
                Router.newIntent(WelcomeActivity.this)
                        .to(WebViewActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.getYs());
                Router.newIntent(WelcomeActivity.this)
                        .to(WebViewActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/c-sjsh.json")
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
                Api.API_BASE_URL = net;
                Thread.sleep(1000);
                jumpPage();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jumpPage() {
        if (isAgree) {
            initUm();
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(WelcomeActivity.this)
                        .to(HomePageActivity.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeActivity.this)
                        .to(LoginActivity.class)
                        .launch();
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialog != null) {
            welcomeDialog.dismiss();
            welcomeDialog = null;
        }
        super.onDestroy();
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (!UMConfigure.isInit) {
            UMConfigure.setLogEnabled(true);
            Log.d("youmeng", "zhuche chenggong");
            //友盟正式初始化
//            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
            UMConfigure.init(this, "6294d48405844627b599751c", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }
}
