package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaiactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jiejijiekuansdafjer.zdfgbaeryazer.R;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiStatusBarUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XActivity;

public class JiuJiJieDaiAboutUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieDaiStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_jiu_ji_jie_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
