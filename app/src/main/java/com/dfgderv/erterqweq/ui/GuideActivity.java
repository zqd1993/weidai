package com.dfgderv.erterqweq.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.dfgderv.erterqweq.R;
import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;
import com.dfgderv.erterqweq.utils.StatusBarUtil;
import com.dfgderv.erterqweq.router.Router;

import com.dfgderv.erterqweq.net.Api;
import com.dfgderv.erterqweq.widget.WelcomeDialog;
import com.umeng.commonsdk.UMConfigure;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuideActivity extends AppCompatActivity {

    private WelcomeDialog welcomeDialog;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    public static int sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    //计算两时间之间的天数差
    public static int diffDays(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

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
        Looper.prepare();
        welcomeDialog = new WelcomeDialog(this, "温馨提示");
        welcomeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    GuideActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeDialog.setOnClickedListener(new WelcomeDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(GuideActivity.this)
                        .to(LoginActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                GuideActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", SharedPreferencesUtilis.getStringFromPref("AGREEMENT") + Api.PRIVACY_POLICY);
                    Router.newIntent(GuideActivity.this)
                            .to(WebViewActivity.class)
                            .data(bundle)
                            .launch();
                }
            }

            @Override
            public void privacyAgreementClicked() {
                if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", SharedPreferencesUtilis.getStringFromPref("AGREEMENT") + Api.USER_SERVICE_AGREEMENT);
                    Router.newIntent(GuideActivity.this)
                            .to(WebViewActivity.class)
                            .data(bundle)
                            .launch();
                }
            }
        });
        welcomeDialog.show();
        Looper.loop();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7742.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
//                        HttpApi.HTTP_API_URL = "http://" + responseData;
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                SharedPreferencesUtilis.saveStringIntoPref("HTTP_API_URL", "http://" + net[0]);
                                SharedPreferencesUtilis.saveStringIntoPref("AGREEMENT", net[1]);
                                Thread.sleep(1000);
                                jumpPage();
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jumpPage() {
        if (isAgree) {
            initUm();
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(GuideActivity.this)
                        .to(HomePageActivity.class)
                        .launch();
            } else {
                Router.newIntent(GuideActivity.this)
                        .to(LoginActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeDialog != null){
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
            UMConfigure.init(this, "62c007ef88ccdf4b7eb8a614", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }
}
