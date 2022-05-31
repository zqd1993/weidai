package com.aklsfasad.fsjhfkk.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.router.Router;
import com.aklsfasad.fsjhfkk.ui.WebHuiMinActivity;
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
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("更多信息");
        zcxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", Api.PRIVACY_POLICY);
            Router.newIntent(this)
                    .to(WebHuiMinActivity.class)
                    .data(bundle)
                    .launch();
        });

        ysxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
            Router.newIntent(this)
                    .to(WebHuiMinActivity.class)
                    .data(bundle)
                    .launch();
        });

        gywmSl.setOnClickListener(v -> {
            Router.newIntent(this)
                    .to(AboutActivityHuiMin.class)
                    .launch();
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
