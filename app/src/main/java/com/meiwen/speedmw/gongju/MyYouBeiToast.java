package com.meiwen.speedmw.gongju;

import android.widget.Toast;

import com.meiwen.speedmw.MainYouBeiApp;

public class MyYouBeiToast {

    private static long lastClick = 0;

    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
    }

    private MyYouBeiToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainYouBeiApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
