package com.biofs.lqgdkic.weifenqijietiaoapi;

import android.content.Context;
import android.util.TypedValue;

import com.biofs.lqgdkic.net.XApi;

public class WeiFenQiJieTiaoApi {
    public static final String ZCXY = "https://gnxys.pycxwl.cn/profile/vo361qbdk/zcxy.html";
    public static final String YSXY= "https://gnxys.pycxwl.cn/profile/vo361qbdk/ysxy.html";
    public static String HTTP_API_URL = "http://47.105.47.183:6610";

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

    private static WeiFenQiJieTiaoInterfaceUtils weiFenQiJieTiaoInterfaceUtils;

    public static WeiFenQiJieTiaoInterfaceUtils getInterfaceUtils() {
        if (weiFenQiJieTiaoInterfaceUtils == null) {
            synchronized (WeiFenQiJieTiaoApi.class) {
                if (weiFenQiJieTiaoInterfaceUtils == null) {
                    weiFenQiJieTiaoInterfaceUtils = XApi.getInstance().getRetrofit(WeiFenQiJieTiaoApi.HTTP_API_URL, true).create(WeiFenQiJieTiaoInterfaceUtils.class);
                }
            }
        }
        return weiFenQiJieTiaoInterfaceUtils;
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
