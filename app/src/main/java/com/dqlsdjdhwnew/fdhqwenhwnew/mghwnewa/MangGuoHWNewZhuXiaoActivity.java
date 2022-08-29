package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.StatusBarUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwneww.MangGuoHWNewRemindDialog;

public class MangGuoHWNewZhuXiaoActivity extends AppCompatActivity {

    private MangGuoHWNewRemindDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilMangGuoHWNew.setTransparent(this, false);
        if (PreferencesOpenUtilMangGuoHWNew.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_mang_guo_hw_new);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new MangGuoHWNewRemindDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new MangGuoHWNewRemindDialog.OnButtonClickListener() {
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
