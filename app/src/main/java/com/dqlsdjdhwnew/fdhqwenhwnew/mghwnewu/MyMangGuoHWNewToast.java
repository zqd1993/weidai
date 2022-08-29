package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu;

import android.widget.Toast;

import com.dqlsdjdhwnew.fdhqwenhwnew.MainMangGuoHWNewApp;

public class MyMangGuoHWNewToast {

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

    private MyMangGuoHWNewToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainMangGuoHWNewApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
