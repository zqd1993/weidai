package com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurkongjian.ClickHWShanJieBeiYongJinTextView;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.mvp.XActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.NetError;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.router.Router;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class OpenHWShanJieBeiYongJinUtil {

    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError){
                HWShanJieBeiYongJinMyToast.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError){
                HWShanJieBeiYongJinMyToast.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError){
                HWShanJieBeiYongJinMyToast.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError){
                HWShanJieBeiYongJinMyToast.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError){
                HWShanJieBeiYongJinMyToast.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError){
                HWShanJieBeiYongJinMyToast.showShort("其他异常");
            }
        }
    }

    public static boolean isMobile(String number) {
        if ((number != null) && (!number.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", number);
        }
        return false;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface
                    .getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface
                        .nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
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
     * 获取本地IP
     *
     * @return
     */
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static List<ClickHWShanJieBeiYongJinTextView.SpanModel> createSpanTexts(){
        List<ClickHWShanJieBeiYongJinTextView.SpanModel> spanModels = new ArrayList<>();
        ClickHWShanJieBeiYongJinTextView.ClickSpanModel spanModel = new ClickHWShanJieBeiYongJinTextView.ClickSpanModel();
        ClickHWShanJieBeiYongJinTextView.SpanModel textSpanModel = new ClickHWShanJieBeiYongJinTextView.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《用户注册协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickHWShanJieBeiYongJinTextView.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new ClickHWShanJieBeiYongJinTextView.ClickSpanModel();
        spanModel.setStr("《隐私政策》");
        spanModels.add(spanModel);

        textSpanModel = new ClickHWShanJieBeiYongJinTextView.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    /**
     * android 7.0及以上 （2）扫描各个网络接口获取mac地址
     * 获取设备HardwareAddress地址
     * 在测试设备能获取到 mac（3399，7.1系统有线网）
     * 需要权限是 android.permission.INTERNET
     * @return
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            hardWareAddress = null;
            if (hardWareAddress != null)
                break;
        }
        return hardWareAddress;
    }


    public static List<ClickHWShanJieBeiYongJinTextView.SpanModel> createDlSpanTexts() {
        List<ClickHWShanJieBeiYongJinTextView.SpanModel> spanModels = new ArrayList<>();
        ClickHWShanJieBeiYongJinTextView.ClickSpanModel spanModel = new ClickHWShanJieBeiYongJinTextView.ClickSpanModel();
        ClickHWShanJieBeiYongJinTextView.SpanModel textSpanModel = new ClickHWShanJieBeiYongJinTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《用户注册协议》");
        spanModels.add(spanModel);

        spanModel = new ClickHWShanJieBeiYongJinTextView.ClickSpanModel();
        spanModel.setStr("《隐私政策》");
        spanModels.add(spanModel);
        return spanModels;
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

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle) {
        jumpPage(activity, to, bundle, false);
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        jumpPage(activity, to, bundle, isFinish);
    }

    public static void jumpPage(Activity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        if (bundle != null) {
            Router.newIntent(activity)
                    .to(to)
                    .data(bundle)
                    .launch();
        } else {
            Router.newIntent(activity)
                    .to(to)
                    .launch();
        }
        if (isFinish) {
            activity.finish();
        }
    }

}
