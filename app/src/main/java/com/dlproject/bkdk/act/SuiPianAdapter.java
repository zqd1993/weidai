package com.dlproject.bkdk.act;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SuiPianAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    /**
     * 判断市场是否存在的方法
     *
     * @param context
     * @param packageName
     *            应用市场包名
     * @return true or false
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> NameList = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从packageInfo中取出包名，放入NameList中
        if (packageInfo != null) {
            for (int i = 0; i < packageInfo.size(); i++) {
                String pn = packageInfo.get(i).packageName;
                NameList.add(pn);
            }
        }
        return NameList.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public SuiPianAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    private void getIp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://pv.sohu.com/cityjson")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
