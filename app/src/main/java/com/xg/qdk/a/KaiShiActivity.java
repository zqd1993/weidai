package com.xg.qdk.a;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xg.qdk.R;
import com.xg.qdk.api.MyApi;
import com.xg.qdk.u.BaseUtil;
import com.xg.qdk.u.PreferencesStaticOpenUtil;
import com.xg.qdk.u.StatusBarUtil;
import com.xg.qdk.w.StartPageRemindDialog;
//import com.umeng.commonsdk.UMConfigure;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class KaiShiActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false;

    private String phone = "";

    private StartPageRemindDialog startPageRemindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qidong);
        StatusBarUtil.setTransparent(this, false);
        isSure = PreferencesStaticOpenUtil.getBool("isSure");
        phone = PreferencesStaticOpenUtil.getString("phone");
        jumpPage();
    }

    private void jumpPage() {
        if (!isSure) {
            startPageRemindDialog = new StartPageRemindDialog(this);
            startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
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
                    BaseUtil.jumpPage(KaiShiActivity.this, DengLuActivity.class);
                    finish();
                }

                @Override
                public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", MyApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    BaseUtil.jumpPage(KaiShiActivity.this, WangYeActivity.class, bundle);
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
                    BaseUtil.jumpPage(KaiShiActivity.this, WangYeActivity.class, bundle);
                }
            });
            startPageRemindDialog.show();
        } else {
            initUm();
            new Handler().postDelayed(() -> {
                if (TextUtils.isEmpty(phone)) {
                    BaseUtil.jumpPage(KaiShiActivity.this, DengLuActivity.class);
                } else {
                    BaseUtil.jumpPage(KaiShiActivity.this, ZhuYeActivity.class);
                }
                finish();
            }, 1000);
        }
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

    private void initUm(){
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
//        if (!UMConfigure.isInit) {
//            UMConfigure.setLogEnabled(true);
//            Log.d("youmeng", "zhuche chenggong");
//            //友盟正式初始化
////            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
//            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
//            // 参数一：当前上下文context；
//            // 参数二：应用申请的Appkey（需替换）；
//            // 参数三：渠道名称；
//            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
//            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
//            UMConfigure.init(this, "629d692e05844627b5a1152a", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        }
    }
}
