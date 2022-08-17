package com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao;

import android.widget.Toast;

import com.lejieqianbaosdfwer.dfgseryaer.LeJieQianBaoMainApp;

public class MyToastLeJieQianBao {

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

    private MyToastLeJieQianBao() {
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(LeJieQianBaoMainApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
