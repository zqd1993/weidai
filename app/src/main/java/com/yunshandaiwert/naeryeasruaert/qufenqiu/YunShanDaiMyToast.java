package com.yunshandaiwert.naeryeasruaert.qufenqiu;

import android.widget.Toast;

import com.yunshandaiwert.naeryeasruaert.YunShanDaiMainApp;

public class YunShanDaiMyToast {

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

    private YunShanDaiMyToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(YunShanDaiMainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
