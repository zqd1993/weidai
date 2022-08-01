package com.jinyu.xiaopu.fenfujieui.fenfujieactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jinyu.xiaopu.FenFuJieApp;
import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.fenfujieutils.FenFuJieStatusBarUtil;
import com.jinyu.xiaopu.mvp.XActivity;

public class FenFuJieAboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView version_code_tv;

    @Override
    public void initData(Bundle savedInstanceState) {
        FenFuJieStatusBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilisFenFuJie.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        version_code_tv.setText(StaticFenFuJieUtil.getAppVersionName(FenFuJieApp.getContext()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_fen_fu_jie;
    }

    @Override
    public Object newP() {
        return null;
    }
}
