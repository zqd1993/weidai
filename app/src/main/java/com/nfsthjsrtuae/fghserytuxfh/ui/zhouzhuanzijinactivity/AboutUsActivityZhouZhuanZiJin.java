package com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.StatusBarUtil;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;

public class AboutUsActivityZhouZhuanZiJin extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_zhou_zhuan_zi_jin;
    }

    @Override
    public Object newP() {
        return null;
    }
}
