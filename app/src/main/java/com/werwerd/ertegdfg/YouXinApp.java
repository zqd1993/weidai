package com.werwerd.ertegdfg;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.umeng.commonsdk.UMConfigure;

import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.NetProvider;
import com.werwerd.ertegdfg.net.RequestHandler;
import com.werwerd.ertegdfg.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by wanglei on 2016/12/31.
 */

public class YouXinApp extends Application {

    private static Context context;

    protected static SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        UMConfigure.preInit(getApplicationContext(), "6294d3ab05844627b599750b", "Umeng");
        /**
         * 打开app首次隐私协议授权，以及sdk初始化，判断逻辑请查看SplashTestActivity
         */
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (SharedPreferencesYouXinUtilis.getStringFromPref("uminit").equals("1")) {
            //友盟正式初始化
            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
        }
        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }

    public static Context getContext() {
        return context;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
