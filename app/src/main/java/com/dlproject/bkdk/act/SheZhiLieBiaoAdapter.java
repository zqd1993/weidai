package com.dlproject.bkdk.act;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.dlproject.bkdk.R;
import com.dlproject.bkdk.bean.SheZhiModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SheZhiLieBiaoAdapter extends BaseQuickAdapter<SheZhiModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    /**
     * 判断市场是否存在的方法
     *
     * @param context
     * @param packageName
     *            应用市场包名
     * @return true or false
     */
    public static boolean wertgdfg(Context context, String packageName) {
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

    public SheZhiLieBiaoAdapter(int layoutResId, @Nullable List<SheZhiModel> data) {
        super(layoutResId, data);
    }

    /**
     * 可以用ExternalCacheDiskCacheFactory来把你的磁盘缓存放到sd卡的公共缓存目录上,这里默认设置150M
     */
    public static void setDiskCacheDir(Context context){
        GlideBuilder builder = new GlideBuilder();
        builder.setDiskCache( new ExternalCacheDiskCacheFactory(context, "ImgCache", 150 * 1024 * 1024));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SheZhiModel item) {
        ImageView imageView = helper.getView(R.id.set_pic);
        helper.setText(R.id.set_name, item.getName());
        Glide.with(mContext).load(item.getPic()).into(imageView);
        helper.getView(R.id.parent_fl).setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClicked(helper.getLayoutPosition());
            }
        });
    }

    /**
     * 清除缓存
     */
    public static  void clearCache(final Context context){
        Glide.get(context).clearMemory(); //清理内存缓存
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache(); //清理磁盘缓存
            }
        }).start();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

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

    public interface OnClickListener {
        void onClicked(int position);
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

}
