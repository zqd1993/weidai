package com.zmansdjhsdn.vpcxkassna;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zmansdjhsdn.vpcxkassna.net.NetError;
import com.zmansdjhsdn.vpcxkassna.net.NetProvider;
import com.zmansdjhsdn.vpcxkassna.net.RequestHandler;
import com.zmansdjhsdn.vpcxkassna.net.XApi;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class MainTiQianHuaDaikuanHwApp extends Application {
    private static MainTiQianHuaDaikuanHwApp sInstance;

    private static Context context;

    protected static SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
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

    public static MainTiQianHuaDaikuanHwApp getInstance() {
        return sInstance;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
