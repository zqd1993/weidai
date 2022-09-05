package com.aklsfasad.fsjhfkk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.NetProvider;
import com.aklsfasad.fsjhfkk.net.RequestHandler;
import com.aklsfasad.fsjhfkk.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by wanglei on 2016/12/31.
 */

public class HuiMinApp extends Application {
    private static HuiMinApp sInstance;

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

    public static HuiMinApp getInstance() {
        return sInstance;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
