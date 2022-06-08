package com.dlproject.bkdk.uti;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.dlproject.bkdk.ParentApp;

public class TiShi {

    private static long lastClick = 0;

    public static boolean isShow = true;

    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
    }

    private TiShi() {
    }

    /**
     * 屏幕中间位置显示短时间Toast
     *
     * @param context
     * @param msg
     */
    public static void ToastShortCenter(Context context, String msg) {
        if (isShow) {
            if (context != null) {
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }

    }

    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(ParentApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 屏幕中心位置长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void ToastLongCenter(Context context, String message) {
        if (isShow) {
            if (context != null) {
                Toast toast = Toast.makeText(context, message,
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void ToastShow(Context context, String message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

}
