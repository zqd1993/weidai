package com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.haojieopdzfgvaewt.bbsdgrgdz.R;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviow.StartPageRemindDialogFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.mvp.XActivity;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.FenQiXiangVioOpenUtil;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou.StatusBarUtilFenQiXiangVio;
import com.umeng.commonsdk.UMConfigure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartPageActivityFenQiXiangVio extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageRemindDialogFenQiXiangVio startPageRemindDialogFenQiXiangVio;

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
        startPageRemindDialogFenQiXiangVio = new StartPageRemindDialogFenQiXiangVio(this);
        startPageRemindDialogFenQiXiangVio.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    StartPageActivityFenQiXiangVio.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageRemindDialogFenQiXiangVio.setOnListener(new StartPageRemindDialogFenQiXiangVio.OnListener() {
            @Override
            public void oneBtnClicked() {
                initUm();
                PreferencesOpenUtilFenQiXiangVio.saveBool("isSure", true);
                startPageRemindDialogFenQiXiangVio.dismiss();
                FenQiXiangVioOpenUtil.jumpPage(StartPageActivityFenQiXiangVio.this, DlFenQiXiangVioActivity.class);
                finish();
            }

            @Override
            public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpApiFenQiXiangVio.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    FenQiXiangVioOpenUtil.jumpPage(StartPageActivityFenQiXiangVio.this, JumpH5FenQiXiangVioActivity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", HttpApiFenQiXiangVio.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    FenQiXiangVioOpenUtil.jumpPage(StartPageActivityFenQiXiangVio.this, JumpH5FenQiXiangVioActivity.class, bundle);
            }
        });
        startPageRemindDialogFenQiXiangVio.show();
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
                                PreferencesOpenUtilFenQiXiangVio.saveString("HTTP_API_URL", "http://" + net[0]);
                                PreferencesOpenUtilFenQiXiangVio.saveString("AGREEMENT", net[1]);
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
                FenQiXiangVioOpenUtil.jumpPage(StartPageActivityFenQiXiangVio.this, DlFenQiXiangVioActivity.class);
            } else {
                FenQiXiangVioOpenUtil.jumpPage(StartPageActivityFenQiXiangVio.this, MainFenQiXiangVioActivity.class);
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

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilFenQiXiangVio.setTransparent(this, false);
        isSure = PreferencesOpenUtilFenQiXiangVio.getBool("isSure");
        phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
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
        return R.layout.activity_start_page_fen_xiang_qi_vio;
    }

    @Override
    public Object newP() {
        return null;
    }
}
