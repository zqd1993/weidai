package com.werwerd.ertegdfg.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lihang.ShadowLayout;
import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.mvp.XActivity;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.router.Router;
import com.werwerd.ertegdfg.ui.WebActivity;
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
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("更多信息");
        appInfoSl.setOnClickListener(v -> {
            Router.newIntent(this)
                    .to(AboutYouXinActivity.class)
                    .launch();
        });
        zcxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", Api.PRIVACY_POLICY);
            Router.newIntent(this)
                    .to(WebActivity.class)
                    .data(bundle)
                    .launch();
        });
        ysxySl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
            Router.newIntent(this)
                    .to(WebActivity.class)
                    .data(bundle)
                    .launch();
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
