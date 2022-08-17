package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.StatusBarUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;

public class AboutUsXianjinChaoShiActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilXianjinChaoShi.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_xian_jin_chao_shi;
    }

    @Override
    public Object newP() {
        return null;
    }
}
