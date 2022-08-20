package com.dlnsg.ytjwhbm.hwshanjiebeiyongjingongju;

import android.widget.Toast;

import com.dlnsg.ytjwhbm.HWShanJieBeiYongJinApp;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class HWShanJieBeiYongJinMyToast {

    private static long lastClick = 0;

    public static String getMacAddr() {
        String mac = "";
        //7.0以上通过IP获取Mac，一下两个方法获取的mac地址不一样
//        mac = getMacAddrByIp();
//        mac = getMachineHardwareAddress();
        mac = "";
        return mac;
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

    private HWShanJieBeiYongJinMyToast() {
    }


    public static void showToast(String text) {
        Toast.makeText(HWShanJieBeiYongJinApp.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int id) {
        Toast.makeText(HWShanJieBeiYongJinApp.getContext().getApplicationContext(), id, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(HWShanJieBeiYongJinApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * android 7.0及以上 （1）根据IP地址获取MAC地址
     * 测试设备 3399（7.1系统，有线网卡）
     * 需要权限是 android.permission.INTERNET
     * @return
     */
    private static String getMacAddrByIp() {
        String strMacAddr = null;
        try {
            // 获得IpD地址
            InetAddress ip = null;
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }
}
