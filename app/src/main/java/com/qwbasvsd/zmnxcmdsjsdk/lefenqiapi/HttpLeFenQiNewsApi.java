package com.qwbasvsd.zmnxcmdsjsdk.lefenqiapi;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;

import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsPreferencesOpenUtil;
import com.qwbasvsd.zmnxcmdsjsdk.net.XApi;

public class HttpLeFenQiNewsApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/opwfqjt/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/opwfqjt/ysxy.html";
    public static String HTTP_API_URL = "http://106.75.13.66:6606";

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private static LeFenQiNewsInterfaceUtils leFenQiNewsInterfaceUtils;

    public static LeFenQiNewsInterfaceUtils getInterfaceUtils() {
        if (leFenQiNewsInterfaceUtils == null) {
            synchronized (HttpLeFenQiNewsApi.class) {
                if (leFenQiNewsInterfaceUtils == null) {
                    leFenQiNewsInterfaceUtils = XApi.getInstance().getRetrofit(HttpLeFenQiNewsApi.HTTP_API_URL, true).create(LeFenQiNewsInterfaceUtils.class);
                }
            }
        }
        return leFenQiNewsInterfaceUtils;
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int nmrtyhfsh(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float werzdh(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float pytugj(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
