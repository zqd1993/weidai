package com.fghyugfg.mjkyhgb;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.net.NetProvider;
import com.fghyugfg.mjkyhgb.net.RequestHandler;
import com.fghyugfg.mjkyhgb.net.XApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class RenRenApp extends Application {

    private static Context context;

    protected static SharedPreferences preferences;

    public static String getLastPhase(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String result = null;
        try {
            Date date = format.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("getLastPhase", "日期数据解析出错，请检查格式");
        } finally {
            return result;
        }
    }

    public static String getSamePhase(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String result = null;
        try {
            Date date = format.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("getLastPhase", "日期数据解析出错，请检查格式");
        } finally {
            return result;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
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

    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }

    public static String getContrastContent(int year, int month) {
        String str = String.valueOf(month);
        if (month < 10) {
            str = "0" + str;
        }
        return year + "-" + str;
    }


    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
