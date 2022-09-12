package com.ruyijiekuandfwetdg.nnrdtydfgsd;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetError;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetProvider;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.RequestHandler;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class RuYiJieKuanAdgFsdfApp extends Application {
    private static RuYiJieKuanAdgFsdfApp sInstance;

    private static Context context;

    protected static SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sInstance = this;
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

    public static RuYiJieKuanAdgFsdfApp getInstance() {
        return sInstance;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
