package com.xvhyrt.ghjtyu.act;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.net.WangLuoApi;
import com.xvhyrt.ghjtyu.uti.GongJuLei;
import com.xvhyrt.ghjtyu.uti.SPFile;
import com.xvhyrt.ghjtyu.uti.ZhuangTaiLanUtil;
import com.xvhyrt.ghjtyu.wei.KaiShiRemindDialog;
import com.umeng.commonsdk.UMConfigure;

public class KaiShiActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false;

    private String phone = "";

    private KaiShiRemindDialog startPageRemindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaishi);
        ZhuangTaiLanUtil.setTransparent(this, false);
        isSure = SPFile.getBool("isSure");
        phone = SPFile.getString("phone");
        jumpPage();
    }

    private void jumpPage() {
        if (!isSure) {
            startPageRemindDialog = new KaiShiRemindDialog(this);
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
            startPageRemindDialog.setOnListener(new KaiShiRemindDialog.OnListener() {
                @Override
                public void oneBtnClicked() {
                    initUm();
                    SPFile.saveBool("isSure", true);
                    GongJuLei.jumpPage(KaiShiActivity.this, DengGeLuActivity.class);
                    finish();
                }

                @Override
                public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", WangLuoApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    GongJuLei.jumpPage(KaiShiActivity.this, JumpH5Activity.class, bundle);
                }

                @Override
                public void twoBtnClicked() {
                    finish();
                }

                @Override
                public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", WangLuoApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    GongJuLei.jumpPage(KaiShiActivity.this, JumpH5Activity.class, bundle);
                }
            });
            startPageRemindDialog.show();
        } else {
            initUm();
            new Handler().postDelayed(() -> {
                if (TextUtils.isEmpty(phone)) {
                    GongJuLei.jumpPage(KaiShiActivity.this, DengGeLuActivity.class);
                } else {
                    GongJuLei.jumpPage(KaiShiActivity.this, ZhongYaoActivity.class);
                }
                finish();
            }, 1000);
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

    private void initUm(){
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
            UMConfigure.init(this, "629eff2005844627b5a41d7f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }
}
