package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.sdyqwjqwias.fdpwejqwdjew.JieDianQianApp;
import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StatusBarUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;

public class AboutUsJieDianQianActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView version_code_tv;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (JieDianQianSharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtilJieDianQian.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        version_code_tv.setText(StaticJieDianQianUtil.getAppVersionName(JieDianQianApp.getContext()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public Object newP() {
        return null;
    }
}
