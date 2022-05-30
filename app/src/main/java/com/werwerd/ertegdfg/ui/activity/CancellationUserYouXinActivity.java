package com.werwerd.ertegdfg.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.utils.StatusBarYouXinUtil;
import com.werwerd.ertegdfg.widget.NormalYouXinDialog;
import com.werwerd.ertegdfg.mvp.XActivity;

public class CancellationUserYouXinActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private NormalYouXinDialog normalYouXinDialog;

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_youxin_user;
    }


    @Override
    protected void onDestroy() {
        if (normalYouXinDialog != null) {
            normalYouXinDialog.dismiss();
            normalYouXinDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarYouXinUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            normalYouXinDialog = new NormalYouXinDialog(this);
            normalYouXinDialog.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> normalYouXinDialog.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        normalYouXinDialog.dismiss();
//                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
//                        Router.newIntent(CancellationAccountActivity.this)
//                                .to(LoginActivity.class)
//                                .launch();
                        finish();
                    }).show();
        });
    }

}
