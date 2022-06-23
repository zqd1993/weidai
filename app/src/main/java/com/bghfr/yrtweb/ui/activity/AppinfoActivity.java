package com.bghfr.yrtweb.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.bghfr.yrtweb.R;
import com.bghfr.yrtweb.MaiApp;
import com.bghfr.yrtweb.utils.MainUtil;
import com.bghfr.yrtweb.utils.StatusBarUtil;
import com.bghfr.yrtweb.mvp.XActivity;

public class AppinfoActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.vs_c_tv)
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
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        versionCodeTv.setText("当前版本号：v" + MainUtil.getAppVersionName(MaiApp.getContext()));
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
        return R.layout.activity_app_info;
    }


}