package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu;

import android.widget.Toast;

import com.linghuojieasdufne.vbdsetrrte.LingHuoJieSvsdFdMainApp;

public class MyToastLingHuoJieSvsdFd {

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

    private MyToastLingHuoJieSvsdFd() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(LingHuoJieSvsdFdMainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
