package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaiactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jiejijiekuansdafjer.zdfgbaeryazer.R;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiStatusBarUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiToastUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XActivity;

public class JiuJiJieDaiCancellationAccountActivity extends XActivity {

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
        JiuJiJieDaiStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            JiuJiJieDaiToastUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_account_jiu_ji_jie_dai;
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
