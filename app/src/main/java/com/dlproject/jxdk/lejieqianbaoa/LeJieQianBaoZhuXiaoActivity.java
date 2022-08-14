package com.dlproject.jxdk.lejieqianbaoa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dlproject.jxdk.R;
import com.dlproject.jxdk.lejieqianbaou.PreferencesOpenUtilLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoStatusBarUtil;
import com.dlproject.jxdk.lejieqianbaow.RemindLeJieQianBaoDialog;

public class LeJieQianBaoZhuXiaoActivity extends AppCompatActivity {

    private RemindLeJieQianBaoDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeJieQianBaoStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLeJieQianBao.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_le_jie_qian_bao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindLeJieQianBaoDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindLeJieQianBaoDialog.OnButtonClickListener() {
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
