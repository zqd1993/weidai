package com.fghyugfg.mjkyhgb.u;

import android.widget.Toast;

import com.fghyugfg.mjkyhgb.RenRenApp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewToast {

    private static long lastClick = 0;

    public static String getYmdDliverCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年\nMM月dd日");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getYmCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        Date now = new Date(time);
        return format.format(now);
    }

    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
    }

    private NewToast() {
    }

    public static String getYmChart(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getYmdChart(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date(time);
        return format.format(now);
    }

    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(RenRenApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static String getYmdWithDot(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getYmdCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date now = new Date(time);
        return format.format(now);
    }
}
