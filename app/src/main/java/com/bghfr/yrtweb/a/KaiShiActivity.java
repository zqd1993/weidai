package com.bghfr.yrtweb.a;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bghfr.yrtweb.R;
import com.bghfr.yrtweb.api.MyApi;
import com.bghfr.yrtweb.mvp.XActivity;
import com.bghfr.yrtweb.u.BaseUtil;
import com.bghfr.yrtweb.u.PreferencesStaticOpenUtil;
import com.bghfr.yrtweb.u.StatusBarUtil;
import com.bghfr.yrtweb.w.StartPageRemindDialog;
import com.umeng.commonsdk.UMConfigure;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KaiShiActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageRemindDialog startPageRemindDialog;

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://ossbj0714.oss-cn-beijing.aliyuncs.com/server7723.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                PreferencesStaticOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                PreferencesStaticOpenUtil.saveString("AGREEMENT", net[1]);
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
        if (isSure) {
            initUm();
            if (TextUtils.isEmpty(phone)) {
                BaseUtil.getValue(KaiShiActivity.this, DengLuActivity.class, null, true);
            } else {
                BaseUtil.getValue(KaiShiActivity.this, ZhuYeActivity.class, null, true);
            }
        } else {
            showDialog();
        }
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
//        Looper.prepare();
        startPageRemindDialog = new StartPageRemindDialog(this);
        startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    KaiShiActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialog.setOnListener(new StartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
                initUm();
                PreferencesStaticOpenUtil.saveString("uminit", "1");
                PreferencesStaticOpenUtil.saveBool("isSure", true);
                startPageRemindDialog.dismiss();
                BaseUtil.getValue(KaiShiActivity.this, DengLuActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", MyApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                BaseUtil.getValue(KaiShiActivity.this, WangYeActivity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", MyApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                BaseUtil.getValue(KaiShiActivity.this, WangYeActivity.class, bundle);
            }
        });
        startPageRemindDialog.show();
//        Looper.loop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static String formatPrice(Double price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String startStr = df.format(price);
        String startDecimal = startStr.split("\\.")[1];
        Double jishu = 0.01;
        Double endVal = 0.0;
        if (startDecimal.length() > 2 && "5".equals(String.valueOf(startDecimal.charAt(2)))) {
            endVal = Double.valueOf(startStr.substring(0, startStr.length() - 1)) + jishu;
        } else {
            endVal = Double.valueOf(df.format(price));
        }
        return df.format(endVal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 显示键盘输入法
    public static void showSoftInput(Context context, View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
            }
        }
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
            UMConfigure.init(this, "629d692e05844627b5a1152a", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        isSure = PreferencesStaticOpenUtil.getBool("isSure");
        phone = PreferencesStaticOpenUtil.getString("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qidong;
    }

    @Override
    public Object newP() {
        return null;
    }
}
