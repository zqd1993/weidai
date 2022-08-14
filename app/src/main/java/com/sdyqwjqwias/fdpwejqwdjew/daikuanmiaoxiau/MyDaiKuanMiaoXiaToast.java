package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau;

import android.widget.Toast;

import com.sdyqwjqwias.fdpwejqwdjew.MainDaiKuanMiaoXiaApp;

public class MyDaiKuanMiaoXiaToast {

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

    private MyDaiKuanMiaoXiaToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainDaiKuanMiaoXiaApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
