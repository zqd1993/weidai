package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdStatusBarUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmw.RemindLingHuoJieSvsdFdDialog;

public class LingHuoJieSvsdFdZhuXiaoActivity extends AppCompatActivity {

    private RemindLingHuoJieSvsdFdDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LingHuoJieSvsdFdStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLingHuoJieSvsdFd.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_ling_huo_jie_djs_urng);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindLingHuoJieSvsdFdDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindLingHuoJieSvsdFdDialog.OnButtonClickListener() {
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
