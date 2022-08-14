package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi.XiangFenQiHttpApi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BaseModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ConfigXiangFenQiEntity;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XActivity;
import com.xiangfencqiasfew.ertaehrstyu.net.ApiSubscriber;
import com.xiangfencqiasfew.ertaehrstyu.net.NetError;
import com.xiangfencqiasfew.ertaehrstyu.net.XApi;
import com.xiangfencqiasfew.ertaehrstyu.router.Router;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiweidgt.ClickTextViewXiangFenQi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OpenXiangFenQiUtil {

    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError) {
                MyXiangFenQiToast.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError) {
                MyXiangFenQiToast.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError) {
                MyXiangFenQiToast.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError) {
                MyXiangFenQiToast.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError) {
                MyXiangFenQiToast.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError) {
                MyXiangFenQiToast.showShort("其他异常");
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
        XiangFenQiHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<ConfigXiangFenQiEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        jumpPage(activity, to, bundle, false);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<ConfigXiangFenQiEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilXiangFenQi.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                jumpPage(activity, to, bundle, false);
                            }
                        }
                    }
                });
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        XiangFenQiHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<ConfigXiangFenQiEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        jumpPage(activity, to, bundle, isFinish);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<ConfigXiangFenQiEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilXiangFenQi.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
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

    public static List<ClickTextViewXiangFenQi.SpanModel> createSpanTexts() {
        List<ClickTextViewXiangFenQi.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewXiangFenQi.ClickSpanModel spanModel = new ClickTextViewXiangFenQi.ClickSpanModel();
        ClickTextViewXiangFenQi.SpanModel textSpanModel = new ClickTextViewXiangFenQi.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextViewXiangFenQi.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new ClickTextViewXiangFenQi.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new ClickTextViewXiangFenQi.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    public static List<ClickTextViewXiangFenQi.SpanModel> createDlSpanTexts() {
        List<ClickTextViewXiangFenQi.SpanModel> spanModels = new ArrayList<>();
        ClickTextViewXiangFenQi.ClickSpanModel spanModel = new ClickTextViewXiangFenQi.ClickSpanModel();
        ClickTextViewXiangFenQi.SpanModel textSpanModel = new ClickTextViewXiangFenQi.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new ClickTextViewXiangFenQi.ClickSpanModel();
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
