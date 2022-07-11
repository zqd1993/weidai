package com.rihdkauecgh.plihgnytrvfws.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.rihdkauecgh.plihgnytrvfws.ui.HomePageActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.WelcomeActivity;

import java.util.regex.Pattern;

public class StaticUtil {

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

    public static void jumpPage(Activity activity, Class<?> to, Bundle bundle){
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
    }

    public static void getValue(XActivity activity, Class<?> to, Bundle bundle) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            Api.getGankService().getValve("VIDEOTAPE")
                    .compose(XApi.<BaseRespModel<ConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<ConfigModel>>getScheduler())
                    .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage(activity, to, bundle);
                        }

                        @Override
                        public void onNext(BaseRespModel<ConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilis.saveBoolIntoPref("NO_RECORD", !gankResults.getData().getVideoTape().equals("0"));
                                }
                            }
                            jumpPage(activity, to, bundle);
                        }
                    });
        }
    }

}
