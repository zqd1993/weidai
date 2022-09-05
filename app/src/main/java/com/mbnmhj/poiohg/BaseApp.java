package com.mbnmhj.poiohg;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.mbnmhj.poiohg.net.NetError;
import com.mbnmhj.poiohg.net.NetProvider;
import com.mbnmhj.poiohg.net.RequestHandler;
import com.mbnmhj.poiohg.net.XApi;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class BaseApp extends Application {

    private static Context context;

    private static BaseApp sInstance;

    protected static SharedPreferences preferences;

    private static final String TAG = "MainApp";

    protected static Uri mImage;
    public static MediaPlayer player = null;
    public static String pushDeviceId;
    public static boolean isAppForeground = true;
    //个人测试key = 88cee67b11b00a718cdad340b5cb5594；
    //公司key = 20105db1b2e7afbf8e2e3c7b425ef709
    private String appKey = "20105db1b2e7afbf8e2e3c7b425ef709";
    /**
     * 网易七鱼客服
     * 这里将YSFOptions提取出来是为了便于后面对七鱼配置的修改
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sInstance = this;
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

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static BaseApp getInstance() {
        return sInstance;
    }
}
