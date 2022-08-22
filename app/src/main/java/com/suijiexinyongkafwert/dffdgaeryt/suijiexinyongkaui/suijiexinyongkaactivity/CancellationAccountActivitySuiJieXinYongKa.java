package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StatusBarUtilSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.ToastSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XActivity;

public class CancellationAccountActivitySuiJieXinYongKa extends XActivity {

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
        StatusBarUtilSuiJieXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            ToastSuiJieXinYongKaUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sui_jie_xin_yong_ka_cancellation_account;
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
