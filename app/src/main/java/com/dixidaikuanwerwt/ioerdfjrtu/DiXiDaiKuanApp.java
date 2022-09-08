package com.dixidaikuanwerwt.ioerdfjrtu;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetError;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetProvider;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.RequestHandler;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by wanglei on 2016/12/31.
 */

public class DiXiDaiKuanApp extends Application {
    private static DiXiDaiKuanApp sInstance;

    private static Context context;

    protected static SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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

    public static DiXiDaiKuanApp getInstance() {
        return sInstance;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
