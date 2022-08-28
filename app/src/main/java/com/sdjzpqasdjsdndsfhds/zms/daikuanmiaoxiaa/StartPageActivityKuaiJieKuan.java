package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.sdjzpqasdjsdndsfhds.zms.R;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaw.StartPageRemindDialog;
import com.sdjzpqasdjsdndsfhds.zms.mvp.XActivity;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.OpenKuaiJieKuanUtil;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.StatusBarKuaiJieKuanUtil;
//import com.umeng.commonsdk.UMConfigure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartPageActivityKuaiJieKuan extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageRemindDialog startPageRemindDialog;

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
                    StartPageActivityKuaiJieKuan.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialog.setOnListener(new StartPageRemindDialog.OnListener() {
            @Override
            public void oneBtnClicked() {
                initUm();
                PreferencesOpenUtilKuaiJieKuan.saveBool("isSure", true);
                startPageRemindDialog.dismiss();
                OpenKuaiJieKuanUtil.jumpPage(StartPageActivityKuaiJieKuan.this, DlKuaiJieKuanActivity.class);
                finish();
            }

            @Override
            public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", KuaiJieKuanHttpApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenKuaiJieKuanUtil.jumpPage(StartPageActivityKuaiJieKuan.this, JumpH5ActivityKuaiJieKuan.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", KuaiJieKuanHttpApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenKuaiJieKuanUtil.jumpPage(StartPageActivityKuaiJieKuan.this, JumpH5ActivityKuaiJieKuan.class, bundle);
            }
        });
        startPageRemindDialog.show();
//        Looper.loop();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7731.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                PreferencesOpenUtilKuaiJieKuan.saveString("HTTP_API_URL", "http://" + net[0]);
                                PreferencesOpenUtilKuaiJieKuan.saveString("AGREEMENT", net[1]);
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
                OpenKuaiJieKuanUtil.jumpPage(StartPageActivityKuaiJieKuan.this, DlKuaiJieKuanActivity.class);
            } else {
                OpenKuaiJieKuanUtil.jumpPage(StartPageActivityKuaiJieKuan.this, MainKuaiJieKuanActivity.class);
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
        super.onDestroy();
    }

    private void initUm() {
//        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
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
//            UMConfigure.init(this, "629eff2005844627b5a41d7f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKuaiJieKuanUtil.setTransparent(this, false);
        isSure = PreferencesOpenUtilKuaiJieKuan.getBool("isSure");
        phone = PreferencesOpenUtilKuaiJieKuan.getString("phone");
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
        return R.layout.activity_start_page_kuai_jie_kuan;
    }

    @Override
    public Object newP() {
        return null;
    }
}