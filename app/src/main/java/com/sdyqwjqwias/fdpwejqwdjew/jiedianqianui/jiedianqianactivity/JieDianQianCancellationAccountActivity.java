package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StatusBarUtilJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianwidget.JieDianQianNormalDialog;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;

public class JieDianQianCancellationAccountActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private JieDianQianNormalDialog jieDianQianNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (JieDianQianSharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusBarUtilJieDianQian.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            jieDianQianNormalDialog = new JieDianQianNormalDialog(this);
            jieDianQianNormalDialog.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> jieDianQianNormalDialog.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        jieDianQianNormalDialog.dismiss();
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
        if (jieDianQianNormalDialog != null) {
            jieDianQianNormalDialog.dismiss();
            jieDianQianNormalDialog = null;
        }
        super.onDestroy();
    }
}
