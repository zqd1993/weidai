package com.fghyugfg.mjkyhgb.tsndka;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.appbar.AppBarLayout;
import com.fghyugfg.mjkyhgb.TSNDKMainApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FragmentAdapterTSNDK extends FragmentStateAdapter {

    private List<Fragment> fragments;

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
            ContentResolver contentResolver = TSNDKMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(TSNDKMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
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

    public FragmentAdapterTSNDK(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void werfhb(AppBarLayout appBar, int offset) {
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
    public int[] lhjdtyu(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean rtysgh() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File mdhrtuy(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = TSNDKMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(TSNDKMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
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

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
