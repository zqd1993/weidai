package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou;

import android.widget.Toast;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.MainKuaiJieJieKuanNewVoApp;

public class MyKuaiJieJieKuanNewVoToast {

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

    private MyKuaiJieJieKuanNewVoToast() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(MainKuaiJieJieKuanNewVoApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
