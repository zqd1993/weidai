package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu;

import android.widget.Toast;

import com.zmansdjhsdn.vpcxkassna.MainTiQianHuaDaikuanHwApp;

public class MyTiQianHuaDaikuanHwToast {

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

    private MyTiQianHuaDaikuanHwToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainTiQianHuaDaikuanHwApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
