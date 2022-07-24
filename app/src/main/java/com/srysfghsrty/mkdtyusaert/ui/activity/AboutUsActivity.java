package com.srysfghsrty.mkdtyusaert.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.utils.StatusBarUtil;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;

public class AboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
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
