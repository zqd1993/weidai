package com.xvhyrt.ghjtyu.uti;

import android.widget.Toast;

import com.xvhyrt.ghjtyu.ParentApp;

public class TiShi {

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

    private TiShi() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(ParentApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
