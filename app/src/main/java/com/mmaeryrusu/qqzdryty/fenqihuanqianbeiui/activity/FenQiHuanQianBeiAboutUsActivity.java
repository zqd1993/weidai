package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusFenQiHuanQianBeiBarUtil;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;

public class FenQiHuanQianBeiAboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusFenQiHuanQianBeiBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_fen_qi_huan_qian_bei;
    }

    @Override
    public Object newP() {
        return null;
    }
}
