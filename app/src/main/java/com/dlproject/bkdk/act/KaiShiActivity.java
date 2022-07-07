package com.dlproject.bkdk.act;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dlproject.bkdk.R;
import com.dlproject.bkdk.net.WangLuoApi;
import com.dlproject.bkdk.uti.GongJuLei;
import com.dlproject.bkdk.uti.SPFile;
import com.dlproject.bkdk.uti.ZhuangTaiLanUtil;
import com.dlproject.bkdk.wei.KaiShiRemindDialog;
import com.umeng.commonsdk.UMConfigure;

import java.util.LinkedHashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KaiShiActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private KaiShiRemindDialog startPageRemindDialog;

    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaishi);
        ZhuangTaiLanUtil.setTransparent(this, false);
        isSure = SPFile.getBool("isSure");
        phone = SPFile.getString("phone");
        sendRequestWithOkHttp();
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
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
        Looper.prepare();
        startPageRemindDialog = new KaiShiRemindDialog(this);
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
                if (!TextUtils.isEmpty(SPFile.getString("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putString("url", SPFile.getString("AGREEMENT") + WangLuoApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    GongJuLei.jumpPage(KaiShiActivity.this, JumpH5Activity.class, bundle);
                }
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                if (!TextUtils.isEmpty(SPFile.getString("AGREEMENT"))) {
                    bundle = new Bundle();
                    bundle.putString("url", SPFile.getString("AGREEMENT") + WangLuoApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    GongJuLei.jumpPage(KaiShiActivity.this, JumpH5Activity.class, bundle);
                }
            }
        });
        startPageRemindDialog.show();
        Looper.loop();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7705.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (!TextUtils.isEmpty(responseData)) {
//                        HttpApi.HTTP_API_URL = "http://" + responseData;
                            if (responseData.contains(",")) {
                                String[] net = responseData.split(",");
                                SPFile.saveString("HTTP_API_URL", "http://" + net[0]);
                                SPFile.saveString("AGREEMENT", net[1]);
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
                GongJuLei.jumpPage(KaiShiActivity.this, DengGeLuActivity.class);
            } else {
                GongJuLei.jumpPage(KaiShiActivity.this, ZhongYaoActivity.class);
            }
            finish();
        } else {
            showDialog();
        }
    }

    /**
     * 版本比较
     *
     * @param nowVersion    app版本
     * @param serverVersion 服务器版本
     * @return
     */
    public static boolean compareVersion(String nowVersion, String serverVersion) {
        if (nowVersion != null && serverVersion != null) {
            String[] nowVersions = nowVersion.split("\\.");
            String[] serverVersions = serverVersion.split("\\.");
            if (nowVersion != null && serverVersion != null
                    && nowVersions.length > 1 && serverVersions.length > 1) {
                int nowVersionFirst = Integer.parseInt(nowVersions[0]);
                int serverVersionFirst = Integer.parseInt(serverVersions[0]);
                int nowVersionSecond = Integer.parseInt(nowVersions[1]);
                int serverVersionSecond = Integer.parseInt(serverVersions[1]);
                if (nowVersionFirst < serverVersionFirst) {
                    return true;
                } else if (nowVersionFirst == serverVersionFirst
                        && nowVersionSecond < serverVersionSecond) {
                    return true;
                }
            }
        }
        return false;
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
            UMConfigure.init(this, "62c6b00905844627b5dd4e42", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    /**
     * BaseRequestModel
     */
    public final static String REQUESTTRACE = "REQUESTTRACE";
    /**
     * GoodsModel
     */
    public final static String GOODSMODEL = "GOODSMODEL";
    /**
     * ADDRESSMODEL
     */
    public final static String ADDRESSMODEL = "ADDRESSMODEL";
    /**
     * 一物一码绑定
     */
    public final static String BUILDINGGOODS = "BUILDINGGOODS";
    public final static String PUODUCTTIME = "PUODUCTTIME";
    /**
     * 移除传值
     */
    public final static String CameraRemove = "CameraRemove";
    /**
     * 全局缓存
     */
    public static LinkedHashMap<String, String> ONEBUILDING = new LinkedHashMap<>();


}
