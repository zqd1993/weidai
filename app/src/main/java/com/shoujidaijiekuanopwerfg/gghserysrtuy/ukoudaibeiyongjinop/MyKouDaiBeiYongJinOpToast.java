package com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop;

import android.widget.Toast;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.MainKouDaiBeiYongJinOpApp;

public class MyKouDaiBeiYongJinOpToast {

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

    private MyKouDaiBeiYongJinOpToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainKouDaiBeiYongJinOpApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
