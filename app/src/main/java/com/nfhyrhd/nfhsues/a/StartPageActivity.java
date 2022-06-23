package com.nfhyrhd.nfhsues.a;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.api.HttpApi;
import com.nfhyrhd.nfhsues.u.OpenUtil;
import com.nfhyrhd.nfhsues.u.PreferencesOpenUtil;
import com.nfhyrhd.nfhsues.u.StatusBarUtil;
import com.nfhyrhd.nfhsues.w.StartPageRemindDialog;
import com.umeng.commonsdk.UMConfigure;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartPageActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false;

    private String phone = "";

    private StartPageRemindDialog startPageRemindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        StatusBarUtil.setTransparent(this, false);
        isSure = PreferencesOpenUtil.getBool("isSure");
        phone = PreferencesOpenUtil.getString("phone");
        sendRequestWithOkHttp();
        if (!isSure) {
            showDialog();
        }
    }

    private void showDialog() {
            startPageRemindDialog = new StartPageRemindDialog(this);
            startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        StartPageActivity.this.finish();
                        return false;
                    }
                    return true;
                }
            });
            startPageRemindDialog.setOnListener(new StartPageRemindDialog.OnListener() {
                @Override
                public void oneBtnClicked() {
                    initUm();
                    PreferencesOpenUtil.saveBool("isSure", true);
                    OpenUtil.jumpPage(StartPageActivity.this, DlActivity.class);
                    finish();
                }

                @Override
                public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtil.jumpPage(StartPageActivity.this, JumpH5Activity.class, bundle);
                }

                @Override
                public void twoBtnClicked() {
                    finish();
                }

                @Override
                public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtil.jumpPage(StartPageActivity.this, JumpH5Activity.class, bundle);
                }
            });
            startPageRemindDialog.show();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7730.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        HttpApi.HTTP_API_URL = "http://" + responseData;
                        Thread.sleep(1000);
                        jumpPage();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jumpPage() {
        if (isSure) {
            initUm();
            if (TextUtils.isEmpty(phone)) {
                OpenUtil.jumpPage(StartPageActivity.this, DlActivity.class);
            } else {
                OpenUtil.jumpPage(StartPageActivity.this, MainActivity.class);
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
            UMConfigure.init(this, "62ab36bc05844627b5b5553c", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }
}
