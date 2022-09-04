package com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qnsdndhsd.dfjsdmwendsj.BuildConfig;
import com.qnsdndhsd.dfjsdmwendsj.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TabWeiXxyongHuaAdapter extends BaseQuickAdapter<MainWeiXxyongHuaActivity.TabModel, BaseViewHolder> {

    private ClickedListener clickedListener;

    public TabWeiXxyongHuaAdapter(int layoutResId, @Nullable List<MainWeiXxyongHuaActivity.TabModel> data) {
        super(layoutResId, data);
    }

    public static PackageManager getmPackageManager(Context context) {
        return context.getPackageManager();
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MainWeiXxyongHuaActivity.TabModel item) {
        ImageView tabImg = helper.getView(R.id.tab_img);
        TextView tabName = helper.getView(R.id.tab_name);
        tabName.setText(item.getName());
        if (item.isChecked()){
            Glide.with(mContext).load(item.getSelectedIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(mContext).load(item.getIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.color_bdbdc5));
        }
        helper.getView(R.id.click_view).setOnClickListener(v -> {
            if (!item.isChecked()){
                item.setChecked(true);
                for (int i = 0; i < getData().size(); i++){
                    if (i != helper.getLayoutPosition()){
                        getData().get(i).setChecked(false);
                    }
                }
                if (clickedListener != null){
                    clickedListener.onClick(helper.getLayoutPosition());
                }
                notifyDataSetChanged();
            }
        });
    }

    public static String versionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int versionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 是否安装应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        } finally {
            return packageInfo != null;
        }
    }

    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public interface ClickedListener{
        void onClick(int position);
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return "";
        }
        String channelNumber = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context
                        .getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (channelNumber.equals("ch_rulimeirong")) {
            channelNumber = BuildConfig.DEBUG ? "如丽测试版" : "如丽正式版";
        }
        return channelNumber;
    }
}
