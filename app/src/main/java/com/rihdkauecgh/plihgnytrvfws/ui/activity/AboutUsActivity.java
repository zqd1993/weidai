package com.rihdkauecgh.plihgnytrvfws.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.App;
import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtil;
import com.rihdkauecgh.plihgnytrvfws.utils.StatusBarUtil;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;

public class AboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView versionCodeTv;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        versionCodeTv.setText("当前版本号：v" + StaticUtil.getAppVersionName(App.getContext()));
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
