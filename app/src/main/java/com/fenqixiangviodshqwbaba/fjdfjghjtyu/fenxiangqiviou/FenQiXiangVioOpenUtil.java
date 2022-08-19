package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioConfigEntity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviow.FenQiXiangVioClickTextView;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.mvp.XActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.ApiSubscriber;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.NetError;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.XApi;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FenQiXiangVioOpenUtil {

    public static void showErrorInfo(Context context, NetError error) {
        if (error != null) {
            if (error.getType() == NetError.ParseError) {
                MyFenQiXiangVioToast.showShort("数据解析异常");
            } else if (error.getType() == NetError.AuthError) {
                MyFenQiXiangVioToast.showShort("身份验证异常");
            } else if (error.getType() == NetError.BusinessError) {
                MyFenQiXiangVioToast.showShort("业务异常");
            } else if (error.getType() == NetError.NoConnectError) {
                MyFenQiXiangVioToast.showShort("网络无连接");
            } else if (error.getType() == NetError.NoDataError) {
                MyFenQiXiangVioToast.showShort("数据为空");
            } else if (error.getType() == NetError.OtherError) {
                MyFenQiXiangVioToast.showShort("其他异常");
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
        HttpApiFenQiXiangVio.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilFenQiXiangVio.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                jumpPage(activity, to, bundle, false);
                            }
                        }
                    }
                });
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        HttpApiFenQiXiangVio.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilFenQiXiangVio.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
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

    public static List<FenQiXiangVioClickTextView.SpanModel> createSpanTexts() {
        List<FenQiXiangVioClickTextView.SpanModel> spanModels = new ArrayList<>();
        FenQiXiangVioClickTextView.ClickSpanModel spanModel = new FenQiXiangVioClickTextView.ClickSpanModel();
        FenQiXiangVioClickTextView.SpanModel textSpanModel = new FenQiXiangVioClickTextView.SpanModel();
        textSpanModel.setStr("为了保障软件服务的安全、运营的质量及效率，我们会收集您的设备信息和服务日志，具体内容请参照隐私条款；" +
                "为了预防恶意程序，确保运营质量及效率，我们会收集安装的应用信息或正在进行的进程信息。" +
                "如果未经您的授权，我们不会使用您的个人信息用于您未授权的其他途径或目的。\n\n" +
                "我们非常重视对您个人信息的保护，您需要同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        textSpanModel = new FenQiXiangVioClickTextView.SpanModel();
        textSpanModel.setStr("和");
        spanModels.add(textSpanModel);

        spanModel = new FenQiXiangVioClickTextView.ClickSpanModel();
        spanModel.setStr("《用户隐私协议》");
        spanModels.add(spanModel);

        textSpanModel = new FenQiXiangVioClickTextView.SpanModel();
        textSpanModel.setStr("，才能继续使用我们的服务。");
        spanModels.add(textSpanModel);
        return spanModels;
    }

    public static List<FenQiXiangVioClickTextView.SpanModel> createDlSpanTexts() {
        List<FenQiXiangVioClickTextView.SpanModel> spanModels = new ArrayList<>();
        FenQiXiangVioClickTextView.ClickSpanModel spanModel = new FenQiXiangVioClickTextView.ClickSpanModel();
        FenQiXiangVioClickTextView.SpanModel textSpanModel = new FenQiXiangVioClickTextView.SpanModel();
        textSpanModel.setStr("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setStr("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new FenQiXiangVioClickTextView.ClickSpanModel();
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
