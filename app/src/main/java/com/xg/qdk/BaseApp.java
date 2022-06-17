package com.xg.qdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xg.qdk.net.NetError;
import com.xg.qdk.net.NetProvider;
import com.xg.qdk.net.RequestHandler;
import com.xg.qdk.net.XApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class BaseApp extends Application {

    private static Context context;

    protected static SharedPreferences preferences;

    public static final int RC_READ_PHONE_STATE = 3001;
    public static final int RC_SETTING = 3000;
    public static final String KEY_LENTH = "lenth";
    public static final String KEY_VALUE_1 = "value_1";
    public static final String KEY_VALUE_2 = "value_2";
    public static final String KEY_VALUE_3 = "value_3";
    public static final String KEY_VALUE_4 = "value_4";
    public static final String KEY_VALUE_5 = "value_5";
    public static final String KEY_VALUE_6 = "value_6";
    public static final int REQ_CODE_1 = 4001;
    public static final int REQ_CODE_2 = 4002;
    public static final int REQ_CODE_3 = 4003;
    public static final int REQ_CODE_4 = 4004;
    public static final int REQ_CODE_5 = 5005;

    public static final int DEFULT_AREA_ID = 0;

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

    public static String getDataFormat(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getTime());
    }

    public static String getDataFormat(long data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);
    }

    public static String getDataFormatyyyyMMddHHmmss(long data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(data);
    }

    public static String getDataFormatyyyyMMddHHmmss(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(data.getTime());
    }


    public static Context getContext() {
        return context;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
