package com.fghyugfg.mjkyhgb.apithreesoxone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.fghyugfg.mjkyhgb.ThreeSixOneMainApp;
import com.fghyugfg.mjkyhgb.net.XApi;
import com.fghyugfg.mjkyhgb.threesoxoneu.PreferencesThreeSixOneOpenUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpApiThreeSixOne {
    public static final String ZCXY = "/profile/op361dk/zcxy.html";
    public static final String YSXY= "/profile/op361dk/ysxy.html";
    public static String HTTP_API_URL = "";

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void setAppBarLayoutOffset(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    /**
     * 获取view坐标
     *
     * @param view
     * @return
     */
    public int[] getViewLoaction(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean isOpenVersion10NewStore() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File uriToFileApiQ(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = ThreeSixOneMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(ThreeSixOneMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private static ThreeSixOneInterfaceUtils threeSixOneInterfaceUtils;

    public static ThreeSixOneInterfaceUtils getInterfaceUtils() {
        if (threeSixOneInterfaceUtils == null && !TextUtils.isEmpty(PreferencesThreeSixOneOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (HttpApiThreeSixOne.class) {
                if (threeSixOneInterfaceUtils == null) {
                    threeSixOneInterfaceUtils = XApi.getInstance().getRetrofit(PreferencesThreeSixOneOpenUtil.getString("HTTP_API_URL"), true).create(ThreeSixOneInterfaceUtils.class);
                }
            }
        }
        return threeSixOneInterfaceUtils;
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void werngfjh(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    /**
     * 获取view坐标
     *
     * @param view
     * @return
     */
    public int[] puiyukhk(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean zxcvvngfu() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File tyjgfjd(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = ThreeSixOneMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(ThreeSixOneMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

}
