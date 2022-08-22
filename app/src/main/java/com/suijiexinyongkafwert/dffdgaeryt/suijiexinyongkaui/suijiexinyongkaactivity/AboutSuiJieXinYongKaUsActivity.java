package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StatusBarUtilSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XActivity;

public class AboutSuiJieXinYongKaUsActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilSuiJieXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_sui_jie_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }
}
