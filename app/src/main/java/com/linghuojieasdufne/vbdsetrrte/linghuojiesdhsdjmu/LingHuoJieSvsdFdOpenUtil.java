package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi.HttpApiLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ConfigEntityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmw.ClickTextViewLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.mvp.XActivity;
import com.linghuojieasdufne.vbdsetrrte.net.ApiSubscriber;
import com.linghuojieasdufne.vbdsetrrte.net.NetError;
import com.linghuojieasdufne.vbdsetrrte.net.XApi;
import com.linghuojieasdufne.vbdsetrrte.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LingHuoJieSvsdFdOpenUtil {

    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError) {
                MyToastLingHuoJieSvsdFd.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError) {
                MyToastLingHuoJieSvsdFd.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError) {
                MyToastLingHuoJieSvsdFd.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError) {
                MyToastLingHuoJieSvsdFd.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError) {
                MyToastLingHuoJieSvsdFd.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError) {
                MyToastLingHuoJieSvsdFd.showShort("其他异常");
            }
        }
    }

    public static boolean isMobile(String number) {
        if ((number != null) && (!number.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", number);
        }
        return false;
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle) {
        HttpApiLingHuoJieSvsdFd.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilLingHuoJieSvsdFd.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                jumpPage(activity, to, bundle, false);
                            }
                        }
                    }
                });
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        HttpApiLingHuoJieSvsdFd.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilLingHuoJieSvsdFd.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                jumpPage(activity, to, bundle, isFinish);
                            }
                        }
                    }
                });
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

    public static List<ClickTextViewLingHuoJieSvsdFd.SpanModel> createSpanTexts() {
        List<ClickTextViewLingHuoJieSvsdFd.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewLingHuoJieSvsdFd.ClickSpanModel spanModel = new ClickTextViewLingHuoJieSvsdFd.ClickSpanModel();
        ClickTextViewLingHuoJieSvsdFd.SpanModel textSpanModel = new ClickTextViewLingHuoJieSvsdFd.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextViewLingHuoJieSvsdFd.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new ClickTextViewLingHuoJieSvsdFd.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextViewLingHuoJieSvsdFd.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    public static List<ClickTextViewLingHuoJieSvsdFd.SpanModel> createDlSpanTexts() {
        List<ClickTextViewLingHuoJieSvsdFd.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewLingHuoJieSvsdFd.ClickSpanModel spanModel = new ClickTextViewLingHuoJieSvsdFd.ClickSpanModel();
        ClickTextViewLingHuoJieSvsdFd.SpanModel textSpanModel = new ClickTextViewLingHuoJieSvsdFd.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewLingHuoJieSvsdFd.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }

    public static void jumpPage(Activity activity, Class<?> to, Bundle bundle) {
        Router.newIntent(activity)
                .to(to)
                .data(bundle)
                .launch();
    }

    public static void jumpPage(Activity activity, Class<?> to) {
        Router.newIntent(activity)
                .to(to)
                .launch();
    }

}
