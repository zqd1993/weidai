package com.qpaskjdudfmdf.ytngnds.qingsongdaiu;

import android.widget.Toast;

import com.qpaskjdudfmdf.ytngnds.MainAppQingSongDai;

public class MyQingSongDaiToast {

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

    private MyQingSongDaiToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainAppQingSongDai.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
