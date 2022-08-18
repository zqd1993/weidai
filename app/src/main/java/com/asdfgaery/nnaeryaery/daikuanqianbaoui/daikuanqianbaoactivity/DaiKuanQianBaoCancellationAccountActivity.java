package com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaoactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.DaiKuanQianBaoStatusBarUtil;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.ToastUtilDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.mvp.XActivity;

public class DaiKuanQianBaoCancellationAccountActivity extends XActivity {

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
        DaiKuanQianBaoStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            ToastUtilDaiKuanQianBao.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activitydi_dai_kuan_qian_bao_cancellation_account;
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
