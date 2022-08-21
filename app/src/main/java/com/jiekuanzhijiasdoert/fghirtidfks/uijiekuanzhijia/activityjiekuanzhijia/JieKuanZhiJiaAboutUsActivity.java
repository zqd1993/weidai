package com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.activityjiekuanzhijia;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jiekuanzhijiasdoert.fghirtidfks.R;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaStatusBarUtil;
import com.jiekuanzhijiasdoert.fghirtidfks.mvp.XActivity;

public class JieKuanZhiJiaAboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        JieKuanZhiJiaStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_jie_kuan_zhi_jia;
    }

    @Override
    public Object newP() {
        return null;
    }
}
