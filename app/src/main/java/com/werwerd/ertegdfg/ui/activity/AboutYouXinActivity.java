package com.werwerd.ertegdfg.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.YouXinApp;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.utils.StatusBarYouXinUtil;
import com.werwerd.ertegdfg.mvp.XActivity;

public class AboutYouXinActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView versionCodeTv;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarYouXinUtil.setTransparent(this, false);
        if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
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
