package com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan;

import android.widget.Toast;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.LingHuoJieHuanMainApp;

public class MyToastLingHuoJieHuan {

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

    private MyToastLingHuoJieHuan() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(LingHuoJieHuanMainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
