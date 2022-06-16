package com.fghyugfg.mjkyhgb.u;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.router.Router;
import com.fghyugfg.mjkyhgb.w.ClickTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class OpenMethodUtil {

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

    public static String getMdCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getTMDTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }


    public static boolean isMobile(String number) {
        if ((number != null) && (!number.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", number);
        }
        return false;
    }

    public static Date getCalenderDate(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date now = null;
        try {
            now = format.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (now == null) {
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                return date;
            } else {
                return now;
            }
        }
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

    public static int getYear(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date now = new Date(time);
        return Integer.parseInt(format.format(now));
    }

    public static int getMonth(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date now = new Date(time);
        return Integer.parseInt(format.format(now));
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

    public static String getYear(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date date = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
            date = format1.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date != null) {
                return format.format(date);
            } else {
                return "";
            }
        }
    }

    public static void jumpPage(Activity activity, Class<?> to, Bundle bundle){
        Router.newIntent(activity)
                .to(to)
                .data(bundle)
                .launch();
    }

    public static void jumpPage(Activity activity, Class<?> to){
        Router.newIntent(activity)
                .to(to)
                .launch();
    }

}
