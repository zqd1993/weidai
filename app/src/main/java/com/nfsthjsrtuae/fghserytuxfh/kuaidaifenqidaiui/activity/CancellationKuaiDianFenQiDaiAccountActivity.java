package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.StatusBarUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.ToastUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;

public class CancellationKuaiDianFenQiDaiAccountActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.cancel_btn)
    TextView cancelBtn;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilKuaiDianFenQiDai.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            ToastUtilKuaiDianFenQiDai.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activitykuai_dian_fen_qi_dai_cancellation_account;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
