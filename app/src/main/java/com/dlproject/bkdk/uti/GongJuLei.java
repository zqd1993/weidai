package com.dlproject.bkdk.uti;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.dlproject.bkdk.net.NetError;
import com.dlproject.bkdk.router.Router;
import com.dlproject.bkdk.wei.DianJiView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GongJuLei {

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


    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError){
                TiShi.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError){
                TiShi.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError){
                TiShi.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError){
                TiShi.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError){
                TiShi.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError){
                TiShi.showShort("其他异常");
            }
        }
    }

    public static boolean isMobile(String number) {
        if ((number != null) && (!number.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", number);
        }
        return false;
    }

    public static String getAppVersion(Context context) {
        String version = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
            if (TextUtils.isEmpty(version) || version.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
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

    public static List<DianJiView.SpanModel> createSpanTexts(){
        List<DianJiView.SpanModel> spanModels = new ArrayList<>();
        DianJiView.ClickSpanModel spanModel = new DianJiView.ClickSpanModel();
        DianJiView.SpanModel textSpanModel = new DianJiView.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new DianJiView.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new DianJiView.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new DianJiView.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean zxvdfg(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 启动App
     *
     * @param context
     */
    public static void tynhg(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void bvngfh(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static List<DianJiView.SpanModel> createDlSpanTexts() {
        List<DianJiView.SpanModel> spanModels = new ArrayList<>();
        DianJiView.ClickSpanModel spanModel = new DianJiView.ClickSpanModel();
        DianJiView.SpanModel textSpanModel = new DianJiView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new DianJiView.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }

    public static void jumpPage(Activity activity, Class<?> to, Bundle bundle){
        Router.newIntent(activity)
                .to(to)
                .data(bundle)
                .launch();
    }

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean yujhn(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 启动App
     *
     * @param context
     */
    public static void yughn(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void cxbfght(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static void jumpPage(Activity activity, Class<?> to){
        Router.newIntent(activity)
                .to(to)
                .launch();
    }

}
