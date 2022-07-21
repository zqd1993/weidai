package com.fdhsdjqqhds.ppfdzabsdvd.qufenqia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiStatusBarUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiw.RemindQuFenQiDialog;

public class ZhuXiaoQuFenQiActivity extends AppCompatActivity {

    private RemindQuFenQiDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuFenQiStatusBarUtil.setTransparent(this, false);
        if (PreferencesQuFenQiOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_qu_fen_qi_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindQuFenQiDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindQuFenQiDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    finish();
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

}
