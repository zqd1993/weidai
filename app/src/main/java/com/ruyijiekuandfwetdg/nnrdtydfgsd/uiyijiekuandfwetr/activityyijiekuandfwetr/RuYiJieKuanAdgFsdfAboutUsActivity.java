package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.activityyijiekuandfwetr;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStatusBarUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XActivity;

public class RuYiJieKuanAdgFsdfAboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        RuYiJieKuanAdgFsdfStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public Object newP() {
        return null;
    }
}
