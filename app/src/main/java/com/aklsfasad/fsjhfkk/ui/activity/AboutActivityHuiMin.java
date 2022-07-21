package com.aklsfasad.fsjhfkk.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.HuiMinApp;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.StatusBarUtilHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XActivity;

public class AboutActivityHuiMin extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView versionCodeTv;

    @Override
    public Object newP() {
        return null;
    }

    public String getOrderCommodityIds() {

        String result = "";
        return result;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilHuiMin.setTransparent(this, false);
        if (SharedPreferencesUtilisHuiMin.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        versionCodeTv.setText("当前版本号：v" + StaticUtilHuiMin.getAppVersionName(HuiMinApp.getContext()));
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return sp
     */
    public static int px2sp(float pxValue) {
        return (int) (0.5f + pxValue / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_youxin;
    }


}
