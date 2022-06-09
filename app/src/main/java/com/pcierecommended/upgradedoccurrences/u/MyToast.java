package com.pcierecommended.upgradedoccurrences.u;

import android.widget.Toast;

import com.pcierecommended.upgradedoccurrences.MainApp;

public class MyToast {

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

    private MyToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
