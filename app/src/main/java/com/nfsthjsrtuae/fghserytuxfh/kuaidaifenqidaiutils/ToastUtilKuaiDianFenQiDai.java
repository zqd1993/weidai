package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils;

import android.content.Context;
import android.widget.Toast;

import com.nfsthjsrtuae.fghserytuxfh.KuaiDianFenQiDaiApp;

public class ToastUtilKuaiDianFenQiDai {

    public static Context sContext;

    private static long lastClickTime =0;

    public static boolean isFastToast() {
        boolean flag =true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime -lastClickTime) >= 500) {
            flag =false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    private ToastUtilKuaiDianFenQiDai() {
    }


    /*public static void register(Context context) {
        sContext = context.getApplicationContext();
    }*/


    private static void check() {
        if (sContext == null) sContext = KuaiDianFenQiDaiApp.getContext();
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ToastUtilKuaiDianFenQiDai.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }


    public static void showShort(int resId) {
        check();
        if (isFastToast()){
            return;
        }
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();

    }


    public static void showShort(String message) {
        check();
        if (isFastToast()){
            return;
        }
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        check();
        if (isFastToast()){
            return;
        }
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        check();
        if (isFastToast()){
            return;
        }
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }
}
