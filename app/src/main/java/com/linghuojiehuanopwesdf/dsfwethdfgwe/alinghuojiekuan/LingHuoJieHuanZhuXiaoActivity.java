package com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanStatusBarUtil;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.wlinghuojiekuan.RemindLingHuoJieHuanDialog;

public class LingHuoJieHuanZhuXiaoActivity extends AppCompatActivity {

    private RemindLingHuoJieHuanDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LingHuoJieHuanStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLingHuoJieHuan.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_ling_huo_jie_huan);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindLingHuoJieHuanDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindLingHuoJieHuanDialog.OnButtonClickListener() {
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
