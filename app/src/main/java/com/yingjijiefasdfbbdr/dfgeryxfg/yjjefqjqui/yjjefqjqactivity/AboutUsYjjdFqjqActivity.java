package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqStatusBarUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XActivity;

public class AboutUsYjjdFqjqActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        YjjdFqjqStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_yjjdfqjq;
    }

    @Override
    public Object newP() {
        return null;
    }
}
