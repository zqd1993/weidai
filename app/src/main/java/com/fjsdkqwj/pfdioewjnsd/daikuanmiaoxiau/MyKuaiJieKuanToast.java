package com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau;

import android.widget.Toast;

import com.fjsdkqwj.pfdioewjnsd.MainKuaiJieKuanApp;

public class MyKuaiJieKuanToast {

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

    private MyKuaiJieKuanToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainKuaiJieKuanApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
