package com.xg.qdk.api;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;

import com.xg.qdk.net.XApi;
import com.xg.qdk.u.PreferencesStaticOpenUtil;

public class MyApi {
    public static final String ZCXY = "/profile/hwyjxyd/zcxy.html";
    public static final String HTTP_API_URL = "http://110.42.64.175:7732";
    public static final String YSXY= "/profile/hwyjxyd/ysxy.html";

    private static JieKouUtils interfaceUtils;

    private Context context;
    private boolean isWidth = true;

    private float mRadius = 0f;//round

    private Paint bitmapPaint;
    private RectF roundRect;
    private BitmapShader bitmapShader;
    private boolean isEnable;
    private float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
    private boolean customerRadius = false;
    private float leftTop = 0f;
    private float rightTop = 0f;
    private float rightBottom = 0f;
    private float leftBottom = 0f;

    public static JieKouUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (MyApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(PreferencesStaticOpenUtil.getString("HTTP_API_URL"), true).create(JieKouUtils.class);
                }
            }
        }
        return interfaceUtils;
    }

    /**
     * 设置四个角的圆角半径
     */
    private void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom, boolean isFlush) {
        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;
        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;

        if (isFlush) {
        }
    }
}
