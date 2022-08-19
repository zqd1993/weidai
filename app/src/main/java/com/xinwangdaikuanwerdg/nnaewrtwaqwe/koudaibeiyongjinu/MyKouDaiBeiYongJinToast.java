package com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu;

import android.widget.Toast;

import com.xinwangdaikuanwerdg.nnaewrtwaqwe.MainAppKouDaiBeiYongJin;

public class MyKouDaiBeiYongJinToast {

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

    private MyKouDaiBeiYongJinToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainAppKouDaiBeiYongJin.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
