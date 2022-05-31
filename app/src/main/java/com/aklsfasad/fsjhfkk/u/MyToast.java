package com.aklsfasad.fsjhfkk.u;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

    private static long lastClick = 0;

    public static Context mContext;

    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
    }

    private MyToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
