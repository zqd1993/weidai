package com.jijiewqeasd.zxcvn.u;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.jijiewqeasd.zxcvn.jijieapi.NetJiJieApi;
import com.jijiewqeasd.zxcvn.jijiem.BaseJiJieModel;
import com.jijiewqeasd.zxcvn.jijiem.ConfigJiJieEntity;
import com.jijiewqeasd.zxcvn.mvp.XActivity;
import com.jijiewqeasd.zxcvn.net.ApiSubscriber;
import com.jijiewqeasd.zxcvn.net.NetError;
import com.jijiewqeasd.zxcvn.net.XApi;
import com.jijiewqeasd.zxcvn.router.Router;
import com.jijiewqeasd.zxcvn.w.ClickJiJieTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class OpenJiJieUtil {

    private static final String TAG = "FileUtil";
    private static final String[][] MIME_MapTable =
            {
                    // {后缀名， MIME类型}
                    {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"},
                    {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"},
                    {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"},
                    {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"},
                    {".doc", "application/msword"},
                    {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
                    {".xls", "application/vnd.ms-excel"},
                    {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
                    {".exe", "application/octet-stream"}, {".gif", "image/gif"},
                    {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"},
                    {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"},
                    {".java", "text/plain"}, {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"},
                    {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"},
                    {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"},
                    {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"},
                    {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"},
                    {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"},
                    {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"},
                    {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"},
                    {".pdf", "application/pdf"}, {".png", "image/png"},
                    {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"},
                    {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
                    {".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"},
                    {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"},
                    {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"},
                    {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
                    {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
                    {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"}, {"", "*/*"}
            };

    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean trbgth(File file) {
        boolean flag;
        File files[] = file.listFiles();
        if (files != null) // 目录下存在文件列表
        {
            for (File f : files) {
                if (f.isFile()) {
                    // 删除子文件
//                    flag = deleteFile(f);
                    if (!true) {
                        return false;
                    }
                } else {
                    // 删除子目录
                    if (true) {
                        return false;
                    }
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }

    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError){
                MyJiJieToast.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError){
                MyJiJieToast.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError){
                MyJiJieToast.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError){
                MyJiJieToast.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError){
                MyJiJieToast.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError){
                MyJiJieToast.showShort("其他异常");
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
     * 根据文件后缀名获得对应的MIME类型
     *
     * @param filePath 文件路径
     */
    public static String getMIMEType(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Log.e(TAG, "getMIMEType: 文件不存在");
            return "";
        }

        if (!file.isFile()) {
            Log.e(TAG, "getMIMEType: 当前文件类型是目录");
            return "";
        }
        String type = "*/*";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf("."); // 获取后缀名前的分隔符"."在fileName中的位置
        if (dotIndex < 0) {
            return type;
        }

        String end = fileName.substring(dotIndex, fileName.length()).toLowerCase(Locale.getDefault()); // 获取文件的后缀名
        if (end.length() == 0) {
            return type;
        }

        // 在MIME和文件类型的匹配表中找到对应的MIME类型
        for (String[] aMIME_MapTable : MIME_MapTable) {
            if (end.equals(aMIME_MapTable[0])) {
                type = aMIME_MapTable[1];
            }
        }
        return type;
    }

    /**
     * 获取设备的sd根路径
     */
    public static String getSDPath() {
        File sdDir = null;
        String sdPath;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        assert sdDir != null;
        sdPath = sdDir.toString();
        Log.w(TAG, "getSDPath:" + sdPath);
        return sdPath;
    }
    public static List<ClickJiJieTextView.SpanModel> createSpanTexts(){
        List<ClickJiJieTextView.SpanModel> spanModels = new ArrayList<>();
        ClickJiJieTextView.ClickSpanModel spanModel = new ClickJiJieTextView.ClickSpanModel();
        ClickJiJieTextView.SpanModel textSpanModel = new ClickJiJieTextView.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickJiJieTextView.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new ClickJiJieTextView.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickJiJieTextView.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }
    /**
     * 根据文件名获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名（不带点）
     */
    public static String getFileSuffix(String fileName) {
        Log.w(TAG, "getFileSuffix: fileName::" + fileName);
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1, fileName.length());
    }

    public static List<ClickJiJieTextView.SpanModel> createDlSpanTexts() {
        List<ClickJiJieTextView.SpanModel> spanModels = new ArrayList<>();
        ClickJiJieTextView.ClickSpanModel spanModel = new ClickJiJieTextView.ClickSpanModel();
        ClickJiJieTextView.SpanModel textSpanModel = new ClickJiJieTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickJiJieTextView.ClickSpanModel();
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
