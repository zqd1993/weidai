package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StatusBarUtilZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.mvp.XActivity;

public class AboutZhouZhuanXinYongKaUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilZhouZhuanXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }
}
