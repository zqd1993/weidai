package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StatusBarQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;

public class AboutUsQueQianMaBossActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarQueQianMaBossUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_que_qian_ma_boss;
    }

    @Override
    public Object newP() {
        return null;
    }
}
