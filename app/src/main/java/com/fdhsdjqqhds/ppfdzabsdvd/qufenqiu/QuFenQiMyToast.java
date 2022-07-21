package com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu;

import android.widget.Toast;

import com.fdhsdjqqhds.ppfdzabsdvd.QuFenQiMainApp;

public class QuFenQiMyToast {

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

    private QuFenQiMyToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(QuFenQiMainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
