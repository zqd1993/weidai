package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStatusBarUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaToastUtil;
import com.jieuacnisdoert.naweodfigety.mvp.XActivity;

public class JieKuanZhiJiaCancellationAccountActivity extends XActivity {

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
        JieKuanZhiJiaStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            JieKuanZhiJiaToastUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_account_jie_kuan_zhi_jia;
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
