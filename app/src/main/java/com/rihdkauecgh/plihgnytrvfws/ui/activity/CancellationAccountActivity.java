package com.rihdkauecgh.plihgnytrvfws.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StatusBarUtil;
import com.rihdkauecgh.plihgnytrvfws.widget.NormalDialog;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;

public class CancellationAccountActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private NormalDialog normalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            normalDialog = new NormalDialog(this);
            normalDialog.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> normalDialog.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        normalDialog.dismiss();
//                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
//                        Router.newIntent(CancellationAccountActivity.this)
//                                .to(LoginActivity.class)
//                                .launch();
                        finish();
                    }).show();
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
        if (normalDialog != null) {
            normalDialog.dismiss();
            normalDialog = null;
        }
        super.onDestroy();
    }
}
