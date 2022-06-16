package com.fghyugfg.mjkyhgb.a;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fghyugfg.mjkyhgb.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Method;
import java.util.List;

public class TabAdapter extends BaseQuickAdapter<MainActivity.TabModel, BaseViewHolder> {

    private ClickedListener clickedListener;

    public TabAdapter(int layoutResId, @Nullable List<MainActivity.TabModel> data) {
        super(layoutResId, data);
    }

    /**
     * 判断xiaomi是否有刘海屏
     * https://dev.mi.com/console/doc/detail?pId=1293
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchXiaoMi(Activity activity) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("getInt", String.class, int.class);
            return (int) (get.invoke(c, "ro.miui.notch", 0)) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MainActivity.TabModel item) {
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

    public static int getNotchHeight(Activity activity) {
        String manufacturer = Build.MANUFACTURER;
        int height = 0;
        if (TextUtils.isEmpty(manufacturer)) {
            return height;
        }  else {
            return height;
        }
    }

    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public interface ClickedListener{
        void onClick(int position);
    }

    private static int getNotchSizeXiaoMiHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            int resourceId = activity.getResources().getIdentifier("notch_height", "dimen", "android");
            if (resourceId > 0) {
                return activity.getResources().getDimensionPixelSize(resourceId);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                WindowInsets windowInsets = null;
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    return displayCutout.getSafeInsetTop();
                } else {
                    return displayCutout.getSafeInsetLeft();
                }
            } catch (Exception e) {

            }
        }
        return 0;
    }

}
