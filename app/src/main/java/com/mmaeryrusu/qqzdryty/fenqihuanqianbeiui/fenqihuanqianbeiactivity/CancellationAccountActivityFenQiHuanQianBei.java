package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeiactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusBarUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;

public class CancellationAccountActivityFenQiHuanQianBei extends XActivity {

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
        StatusBarUtilFenQiHuanQianBei.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            ToastUtilFenQiHuanQianBei.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_qi_huan_qian_cancellation_account;
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
