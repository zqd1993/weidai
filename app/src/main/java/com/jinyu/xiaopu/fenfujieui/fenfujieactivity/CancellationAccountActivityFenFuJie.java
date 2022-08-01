package com.jinyu.xiaopu.fenfujieui.fenfujieactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.FenFuJieStatusBarUtil;
import com.jinyu.xiaopu.fenfujiewidget.NormalDialogFenFuJie;
import com.jinyu.xiaopu.mvp.XActivity;

public class CancellationAccountActivityFenFuJie extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private NormalDialogFenFuJie normalDialogFenFuJie;

    @Override
    public void initData(Bundle savedInstanceState) {
        FenFuJieStatusBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilisFenFuJie.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            normalDialogFenFuJie = new NormalDialogFenFuJie(this);
            normalDialogFenFuJie.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> normalDialogFenFuJie.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        normalDialogFenFuJie.dismiss();
//                        SharedPreferencesUtilisFenFuJie.saveStringIntoPref("phone", "");
//                        Router.newIntent(CancellationAccountActivityFenFuJie.this)
//                                .to(LoginFenFuJieActivity.class)
//                                .launch();
                        finish();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_fu_jie_cancellation_account;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onDestroy() {
        if (normalDialogFenFuJie != null) {
            normalDialogFenFuJie.dismiss();
            normalDialogFenFuJie = null;
        }
        super.onDestroy();
    }
}
