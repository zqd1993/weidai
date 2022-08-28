package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import com.fvhrnthdfggeinihua.hdfghuerungf.net.XApi;

public class OpNewGeiNiHuaDaiKuanHttpApi {
    public static final String ZCXY = "https://openxy.huaqibuy.com/profile/opgnhdk/zcxy.html";
    public static final String YSXY= "https://openxy.huaqibuy.com/profile/opgnhdk/ysxy.html";
    public static String HTTP_API_URL = "http://117.50.185.81:7739";

    private static InterfaceGeiNiHuaDaiKuanOpNewUtils interfaceGeiNiHuaDaiKuanOpNewUtils;

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public static InterfaceGeiNiHuaDaiKuanOpNewUtils getInterfaceUtils() {
        if (interfaceGeiNiHuaDaiKuanOpNewUtils == null) {
            synchronized (OpNewGeiNiHuaDaiKuanHttpApi.class) {
                if (interfaceGeiNiHuaDaiKuanOpNewUtils == null) {
                    interfaceGeiNiHuaDaiKuanOpNewUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceGeiNiHuaDaiKuanOpNewUtils.class);
                }
            }
        }
        return interfaceGeiNiHuaDaiKuanOpNewUtils;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap ertyfghsrtuy(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap mdtuyjsrhrt(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }
}
