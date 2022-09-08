package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils;

import android.widget.Toast;

import com.newmazhaocaiewkfd.drngfs.MainAppZhaoCaiAdfm;

public class MyZhaoCaiAdfmToas {

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

    private MyZhaoCaiAdfmToas() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainAppZhaoCaiAdfm.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
