package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanStatusBarUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XActivity;

public class AboutUsDiXiDaiKuanActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        DiXiDaiKuanStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_di_xi_dai_kuan;
    }

    @Override
    public Object newP() {
        return null;
    }
}
