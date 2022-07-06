package com.jijiewqeasd.zxcvn.jijieapi;

import android.text.TextUtils;
import android.util.Log;

import com.jijiewqeasd.zxcvn.net.XApi;
import com.jijiewqeasd.zxcvn.u.PreferencesJiJieOpenUtil;

public class NetJiJieApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/ophmjk/zcxy.html";
    public static final String HTTP_API_URL = "http://45.120.154.46:7712";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/ophmjk/ysxy.html";

    // 如果想屏蔽所有log,可以设置为0
    public static final int LOG_LEVEL = 6;

    public static final int VERBOSE = 5;
    public static final int DEBUG = 4;
    public static final int INFO = 3;
    public static final int WARN = 2;
    public static final int ERROR = 1;

    private static InterfaceJiJieUtils interfaceUtils;

    public static InterfaceJiJieUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (NetJiJieApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"), true).create(InterfaceJiJieUtils.class);
                }
            }
        }
        return interfaceUtils;
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL > WARN) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(tag, msg);
        }
    }

    public static void v(String msg) {
        if (LOG_LEVEL > VERBOSE) {
            Log.v(getCallerName(), msg);
        }
    }

    public static void d(String msg) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(getCallerName(), msg);
        }
    }

    public static void i(String msg) {
        if (LOG_LEVEL > INFO) {
            Log.i(getCallerName(), msg);
        }
    }

    public static void w(String msg) {
        if (LOG_LEVEL > WARN) {
            Log.w(getCallerName(), msg);
        }
    }

    public static void e(String msg) {
        if (LOG_LEVEL > ERROR) {
            Log.e(getCallerName(), msg);
        }
    }

    /**
     * 获取调用者的类名
     */
    public static String getCallerName() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String className = caller.getClassName();// 带有包名信息
        className = className.substring(className.lastIndexOf(".") + 1);
        return className;
    }

    /**
     *  描述：日志内容多的时候(超过4k)需要打印全时.
     */
    public static void showLog(String str) {
        str = str.trim();
        int index = 0;
        int maxLength = 4000;
        String finalString;
        while (index < str.length()) {
            if (str.length() <= index + maxLength) {
                finalString = str.substring(index);
            } else {
                finalString = str.substring(index, maxLength);
            }
            index += maxLength;
            i(getCallerName(), finalString.trim());
        }
    }

}