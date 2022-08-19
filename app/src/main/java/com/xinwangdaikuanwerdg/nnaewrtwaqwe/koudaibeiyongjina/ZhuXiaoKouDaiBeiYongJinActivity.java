package com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjina;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xinwangdaikuanwerdg.nnaewrtwaqwe.R;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinu.StatusBarKouDaiBeiYongJinUtil;
import com.xinwangdaikuanwerdg.nnaewrtwaqwe.koudaibeiyongjinw.RemindKouDaiBeiYongJinDialog;

public class ZhuXiaoKouDaiBeiYongJinActivity extends AppCompatActivity {

    private RemindKouDaiBeiYongJinDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKouDaiBeiYongJinUtil.setTransparent(this, false);
        if (KouDaiBeiYongJinPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_kou_dai_bei_yong_jin_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindKouDaiBeiYongJinDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindKouDaiBeiYongJinDialog.OnButtonClickListener() {
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
