package com.jinyu.xiaopu.fenfujieutils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieConfigModel;
import com.jinyu.xiaopu.mvp.XActivity;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;
import com.jinyu.xiaopu.fenfujienet.ApiSubscriber;
import com.jinyu.xiaopu.fenfujienet.NetError;
import com.jinyu.xiaopu.fenfujienet.XApi;
import com.jinyu.xiaopu.router.Router;

import java.util.regex.Pattern;

public class StaticFenFuJieUtil {

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

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle) {
            FenFuJieApi.getGankService().getValue("VIDEOTAPE")
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(activity.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<FenFuJieConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModelFenFuJie<FenFuJieConfigModel> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    SharedPreferencesUtilisFenFuJie.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                    jumpPage(activity, to, bundle, false);
                                }
                            }
                        }
                    });
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle, boolean isFinish) {
            FenFuJieApi.getGankService().getValue("VIDEOTAPE")
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(activity.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<FenFuJieConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModelFenFuJie<FenFuJieConfigModel> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    SharedPreferencesUtilisFenFuJie.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                    jumpPage(activity, to, bundle, isFinish);
                                }
                            }
                        }
                    });
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

}
