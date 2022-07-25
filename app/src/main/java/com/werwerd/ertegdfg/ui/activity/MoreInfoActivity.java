package com.werwerd.ertegdfg.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lihang.ShadowLayout;
import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.mvp.XActivity;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.router.Router;
import com.werwerd.ertegdfg.ui.WebActivity;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.utils.StatusBarYouXinUtil;
import com.werwerd.ertegdfg.widget.WelcomeYouXinDialog;

import butterknife.BindView;

public class MoreInfoActivity extends XActivity {

    @BindView(R.id.app_info_sl)
    ShadowLayout appInfoSl;
    @BindView(R.id.zcxy_sl)
    ShadowLayout zcxySl;
    @BindView(R.id.ysxy_sl)
    ShadowLayout ysxySl;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarYouXinUtil.setTransparent(this, false);
        if (SharedPreferencesYouXinUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("更多信息");
        appInfoSl.setOnClickListener(v -> {
            StaticYouXinUtil.getValue(this, AboutYouXinActivity.class, null);
        });
        zcxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", Api.PRIVACY_POLICY);
            StaticYouXinUtil.getValue(this, WebActivity.class, bundle);
        });
        ysxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
            StaticYouXinUtil.getValue(this, WebActivity.class, bundle);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_more_info;
    }

    @Override
    public Object newP() {
        return null;
    }
}
