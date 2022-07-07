package com.dlproject.bkdk.act;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dlproject.bkdk.net.WangLuoApi;
import com.dlproject.bkdk.R;
import com.dlproject.bkdk.bean.ParentModel;
import com.dlproject.bkdk.bean.PeiZhiEntity;
import com.dlproject.bkdk.bean.DengGeLuModel;
import com.dlproject.bkdk.mvp.XActivity;
import com.dlproject.bkdk.net.ApiSubscriber;
import com.dlproject.bkdk.net.NetError;
import com.dlproject.bkdk.net.XApi;
import com.dlproject.bkdk.uti.TiShi;
import com.dlproject.bkdk.uti.GongJuLei;
import com.dlproject.bkdk.uti.SPFile;
import com.dlproject.bkdk.uti.ZhuangTaiLanUtil;
import com.dlproject.bkdk.wei.DianJiView;
import com.dlproject.bkdk.wei.DaoJiShiTimer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DengGeLuActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private DianJiView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    /**
     * 历史地址
     */
    public final static String HISTORYADDRESS = "HISTORYADDRESS";
    /**
     * 扫描类型
     */
    public final static String QRCODETYPE = "QRCODETYPE";

    public final static String BARCODE = "BARCODE";

    //    获取默认文件存储目录
    public static String getUpdateFileDir() {
        return "amez/apk/";
    }

    public final static String INTENT_KEY = "update_dialog_values";

    // 360手机助手
    public static final String MARKET = "com.qihoo.appstore";
    // 淘宝手机助手
    public static final String MARKET_TAOBAO = "com.taobao.appcenter";
    // 应用宝
    public static final String MARKET_QQDOWNLOADER = "com.tencent.Android.qqdownloader";
    // 安卓市场
    public static final String MARKET_HIAPK = "com.hiapk.marketpho";
    // 安智市场
    public static final String MARKET_GOAPK = "cn.goapk.market";
    // 包名
    public static final String APP_PACKAGE_NAME = "com.*.*";

    @Override
    public void initData(Bundle savedInstanceState) {
        ZhuangTaiLanUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.view_zaijia, null));
        getConfig();
        readTv.setText(GongJuLei.createDlSpanTexts(), position -> {
                    if (!TextUtils.isEmpty(SPFile.getString("AGREEMENT"))) {
                        bundle = new Bundle();
                        if (position == 1) {
                            bundle.putString("url", SPFile.getString("AGREEMENT") + WangLuoApi.ZCXY);
                            bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                        } else {
                            bundle.putString("url", SPFile.getString("AGREEMENT") + WangLuoApi.YSXY);
                            bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                        }
                        GongJuLei.jumpPage(DengGeLuActivity.this, JumpH5Activity.class, bundle);
                    }
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                TiShi.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                TiShi.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                TiShi.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                TiShi.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    /**
     * 跳转到应用市场
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_denglu;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 启动App
     *
     * @param context
     */
    public static void launchapp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(SPFile.getString("HTTP_API_URL"))) {
            WangLuoApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<ParentModel<PeiZhiEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            GongJuLei.showErrorInfo(DengGeLuActivity.this, error);
                        }

                        @Override
                        public void onNext(ParentModel<PeiZhiEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    SPFile.saveString("app_mail", configEntity.getData().getAppMail());
                                    if ("0".equals(configEntity.getData().getIsCodeLogin())) {
                                        yzmCv.setVisibility(View.GONE);
                                    } else {
                                        yzmCv.setVisibility(View.VISIBLE);
                                    }
                                    isNeedYzm = "1".equals(configEntity.getData().getIsCodeLogin());
                                    isChecked = "1".equals(configEntity.getData().getIsSelectLogin());
                                    remindCb.setChecked(isChecked);
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void getIp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://pv.sohu.com/cityjson")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            ip = jsonObject.getString("cip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断市场是否存在的方法
     *
     * @param context
     * @param packageName
     *            应用市场包名
     * @return true or false
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> NameList = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从packageInfo中取出包名，放入NameList中
        if (packageInfo != null) {
            for (int i = 0; i < packageInfo.size(); i++) {
                String pn = packageInfo.get(i).packageName;
                NameList.add(pn);
            }
        }
        return NameList.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public void login(String phone, String verificationStr) {
        if (!TextUtils.isEmpty(SPFile.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            WangLuoApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<ParentModel<DengGeLuModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            GongJuLei.showErrorInfo(DengGeLuActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(ParentModel<DengGeLuModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    GongJuLei.jumpPage(DengGeLuActivity.this, ZhongYaoActivity.class);
                                    int mobileType = dlModel.getData().getMobileType();
                                    SPFile.saveString("ip", ip);
                                    SPFile.saveString("phone", phone);
                                    SPFile.saveInt("mobileType", mobileType);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    TiShi.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(SPFile.getString("HTTP_API_URL"))) {
            WangLuoApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<ParentModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            GongJuLei.showErrorInfo(DengGeLuActivity.this, error);
                        }

                        @Override
                        public void onNext(ParentModel parentModel) {
                            if (parentModel != null) {
                                if (parentModel.getCode() == 200) {
                                    TiShi.showShort("验证码发送成功");
                                    DaoJiShiTimer cdt = new DaoJiShiTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
