package com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaoactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfStatusBarUtil;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XActivity;

public class AboutUsJiuJiJieTiaojghsdfActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieTiaojghsdfStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_jiu_ji_jie_tiao_boss;
    }

    @Override
    public Object newP() {
        return null;
    }
}
