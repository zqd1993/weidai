package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils;

import android.widget.Toast;

import com.xiangfencqiasfew.ertaehrstyu.MainAppXiangFenQi;

public class MyXiangFenQiToast {

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

    private MyXiangFenQiToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainAppXiangFenQi.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
