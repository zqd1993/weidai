package com.srysfghsrty.mkdtyusaert.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.utils.StatusBarUtil;
import com.srysfghsrty.mkdtyusaert.utils.ToastUtil;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;

public class CancellationAccountActivity extends XActivity {

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
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            ToastUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_account;
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
