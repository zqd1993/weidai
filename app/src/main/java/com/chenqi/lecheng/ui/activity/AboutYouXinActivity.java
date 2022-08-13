package com.chenqi.lecheng.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.YouXinApp;
import com.chenqi.lecheng.utils.SharedPreferencesYouXinUtilis;
import com.chenqi.lecheng.utils.StaticYouXinUtil;
import com.chenqi.lecheng.utils.StatusBarYouXinUtil;
import com.chenqi.lecheng.mvp.XActivity;

public class AboutYouXinActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView versionCodeTv;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarYouXinUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        versionCodeTv.setText("当前版本号：v" + StaticYouXinUtil.getAppVersionName(YouXinApp.getContext()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_youxin;
    }

    @Override
    public Object newP() {
        return null;
    }

    public String getOrderCommodityIds() {

        String result = "";
        return result;
    }

}
