package com.bghfr.yrtweb.api;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;

import com.bghfr.yrtweb.net.XApi;
import com.bghfr.yrtweb.u.PreferencesStaticOpenUtil;

public class MyApi {
    public static final String ZCXY = "https://xyssml.yiqian888.xyz/profile/ljfq/zcxy.html";
    public static final String HTTP_API_URL = "http://106.75.13.66:7748";
    public static final String YSXY = "https://xyssml.yiqian888.xyz/profile/ljfq/ysxy.html";

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
        if (interfaceUtils == null) {
            synchronized (MyApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(JieKouUtils.class);
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
