package com.werwerd.ertegdfg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.ConfigYouXinModel;
import com.werwerd.ertegdfg.mvp.XActivity;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.net.ApiSubscriber;
import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.XApi;
import com.werwerd.ertegdfg.router.Router;

import java.util.regex.Pattern;

public class StaticYouXinUtil {

    public static void showError(Context context, NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    Toast.makeText(context, "数据解析异常", Toast.LENGTH_SHORT).show();
                    break;

                case NetError.AuthError:
                    Toast.makeText(context, "身份验证异常", Toast.LENGTH_SHORT).show();
                    break;

                case NetError.BusinessError:
                    Toast.makeText(context, "业务异常", Toast.LENGTH_SHORT).show();
                    break;

                case NetError.NoConnectError:
                    Toast.makeText(context, "网络无连接", Toast.LENGTH_SHORT).show();
                    break;

                case NetError.NoDataError:
                    Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT).show();
                    break;

                case NetError.OtherError:
                    Toast.makeText(context, "其他异常", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo p1 = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = p1.versionName;
            if (TextUtils.isEmpty(versionName) || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle) {
        Api.getGankService().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<ConfigYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<ConfigYouXinModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SharedPreferencesYouXinUtilis.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                jumpPage(activity, to, bundle, false);
                            }
                        }
                    }
                });
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
        Api.getGankService().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(activity.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespYouXinModel<ConfigYouXinModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespYouXinModel<ConfigYouXinModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SharedPreferencesYouXinUtilis.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                jumpPage(activity, to, bundle, true);
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

}
