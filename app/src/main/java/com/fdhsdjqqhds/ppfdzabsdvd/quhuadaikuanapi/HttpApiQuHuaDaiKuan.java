package com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanapi;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanutils.PreferencesOpenUtilQuHuaDaiKuan;

public class HttpApiQuHuaDaiKuan {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/xyqhdk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/xyqhdk/ysxy.html";
    public static String HTTP_API_URL = "http://47.98.62.38:7757";

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private static QuHuaDaiKuanInterfaceUtils quHuaDaiKuanInterfaceUtils;

    public static QuHuaDaiKuanInterfaceUtils getInterfaceUtils() {
        if (quHuaDaiKuanInterfaceUtils == null) {
            synchronized (HttpApiQuHuaDaiKuan.class) {
                if (quHuaDaiKuanInterfaceUtils == null) {
                    quHuaDaiKuanInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(QuHuaDaiKuanInterfaceUtils.class);
                }
            }
        }
        return quHuaDaiKuanInterfaceUtils;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int puikfyuty(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int ertyxfhrtu(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int mtdjktrdy(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
