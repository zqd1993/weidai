package com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.StatusBarUtilWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;

public class AboutWanRongXinYongKaUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilWanRongXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_wan_rong_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }
}
