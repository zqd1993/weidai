package com.aklsfasad.fsjhfkk.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.ui.HomePageActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.TanPingActivity;
import com.aklsfasad.fsjhfkk.ui.WebHuiMinActivity;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.StatusBarUtilHuiMin;
import com.lihang.ShadowLayout;

import butterknife.BindView;

public class MoreSettingActivity extends XActivity {

    @BindView(R.id.zcxy_sl)
    ShadowLayout zcxySl;
    @BindView(R.id.ysxy_sl)
    ShadowLayout ysxySl;
    @BindView(R.id.gywm_sl)
    ShadowLayout gywmSl;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilHuiMin.setTransparent(this, false);
        if (SharedPreferencesUtilisHuiMin.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("更多信息");
        zcxySl.setOnClickListener(v -> {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", Api.PRIVACY_POLICY);
                StaticUtilHuiMin.getValue(this, WebHuiMinActivity.class, bundle);
        });

        ysxySl.setOnClickListener(v -> {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                StaticUtilHuiMin.getValue(this, WebHuiMinActivity.class, bundle);
        });

        gywmSl.setOnClickListener(v -> {
            StaticUtilHuiMin.getValue(this, AboutActivityHuiMin.class, null);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
