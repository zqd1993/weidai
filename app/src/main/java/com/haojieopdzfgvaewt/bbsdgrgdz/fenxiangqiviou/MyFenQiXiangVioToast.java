package com.haojieopdzfgvaewt.bbsdgrgdz.fenxiangqiviou;

import android.widget.Toast;

import com.haojieopdzfgvaewt.bbsdgrgdz.MainFenQiXiangVioApp;

public class MyFenQiXiangVioToast {

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

    private MyFenQiXiangVioToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainFenQiXiangVioApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
