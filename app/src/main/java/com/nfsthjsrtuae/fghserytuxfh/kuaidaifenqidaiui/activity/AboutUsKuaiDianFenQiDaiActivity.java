package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.StatusBarUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;

public class AboutUsKuaiDianFenQiDaiActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilKuaiDianFenQiDai.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_kuai_dian_fen_qi_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
