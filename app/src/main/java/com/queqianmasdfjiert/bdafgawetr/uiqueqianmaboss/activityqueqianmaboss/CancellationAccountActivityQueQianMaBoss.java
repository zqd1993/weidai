package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StatusBarQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.ToastQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;

public class CancellationAccountActivityQueQianMaBoss extends XActivity {

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
        StatusBarQueQianMaBossUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            ToastQueQianMaBossUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_que_qian_ma_boss_cancellation_account;
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
