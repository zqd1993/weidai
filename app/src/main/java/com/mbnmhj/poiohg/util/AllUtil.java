package com.mbnmhj.poiohg.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.mbnmhj.poiohg.BaseApp;
import com.mbnmhj.poiohg.entity.CFEntity;
import com.mbnmhj.poiohg.entity.MainModel;
import com.mbnmhj.poiohg.entity.MoreModel;
import com.mbnmhj.poiohg.mvp.XActivity;
import com.mbnmhj.poiohg.net.ApiSubscriber;
import com.mbnmhj.poiohg.net.NetApi;
import com.mbnmhj.poiohg.net.NetError;
import com.mbnmhj.poiohg.net.XApi;
import com.mbnmhj.poiohg.router.Router;
import com.mbnmhj.poiohg.view.ClickTextView;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AllUtil {

    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError){
                NewToast.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError){
                NewToast.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError){
                NewToast.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError){
                NewToast.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError){
                NewToast.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError){
                NewToast.showShort("其他异常");
            }
        }
    }

    public static void copyString(String str) {
        ClipboardManager cm = (ClipboardManager) BaseApp.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", str);
        cm.setPrimaryClip(mClipData);
    }

    //金额数字
    public static String getAmountStr(BigDecimal data){
        if (data == null){
            return "0.00";
        }
        return ""+data.setScale(2, BigDecimal.ROUND_HALF_UP);
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

    public static List<ClickTextView.SpanModel> createSpanTexts(){
        List<ClickTextView.SpanModel> spanModels = new ArrayList<>();
        ClickTextView.ClickSpanModel spanModel = new ClickTextView.ClickSpanModel();
        ClickTextView.SpanModel textSpanModel = new ClickTextView.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextView.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new ClickTextView.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextView.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    /**
     * 打印小票
     *
     * @param activity
     * @param requestCode
     * @param data
     * @param tag         打印配送单还是厨打单 1=配送单 2=厨打单
     */
    public static void print(Activity activity, int requestCode, String page, MoreModel data, int tag) {
        if (activity == null)
            return;
        try {
            Intent intent = new Intent();
            //设置跳转目标，用于启动POS打印程序
            intent.setComponent(new ComponentName("com.allinpay.usdk", "com.allinpay.usdk.MainActivity"));
            Bundle bundle = new Bundle();
            JSONObject json = new JSONObject();
            json.put("BUSINESS_ID", "900300003");
            //需要打印的联数
            json.put("PRINT_APPEND_PAGE", page);
            json.put("PRINT_VERSION", "1");
            List<JSONObject> param = new ArrayList<>();
            json.put("PRINT_APPEND_CONTENT", param);
            bundle.putSerializable("REQUEST", json.toString());
            intent.putExtras(bundle);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ClickTextView.SpanModel> createDlSpanTexts() {
        List<ClickTextView.SpanModel> spanModels = new ArrayList<>();
        ClickTextView.ClickSpanModel spanModel = new ClickTextView.ClickSpanModel();
        ClickTextView.SpanModel textSpanModel = new ClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextView.ClickSpanModel();
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
     * 手机号 间隔
     *
     * @param mobile
     * @return
     */
    public static String getMobileByChar(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return mobile;
        }
        if (mobile.contains("-")) {
            return mobile;
        }
        StringBuffer sb = new StringBuffer(mobile);
        sb.insert(7, " ");
        sb.insert(3, " ");
        return sb.toString().trim();
    }

    public static void jumpPage(Activity activity, Class<?> to){
        Router.newIntent(activity)
                .to(to)
                .launch();
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle) {
        jumpPage(activity, to, bundle, false);
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        jumpPage(activity, to, bundle, isFinish);
    }

    public static void jumpPage(Activity activity, Class<?> to, Bundle bundle, boolean isFinish){
        if (bundle != null){
            Router.newIntent(activity)
                    .to(to)
                    .data(bundle)
                    .launch();
        } else {
            Router.newIntent(activity)
                    .to(to)
                    .launch();
        }
        if (isFinish){
            activity.finish();
        }
    }

}
