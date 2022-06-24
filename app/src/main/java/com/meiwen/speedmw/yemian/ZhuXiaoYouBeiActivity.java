package com.meiwen.speedmw.yemian;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.meiwen.speedmw.R;
import com.meiwen.speedmw.chajian.YouBeiRemindDialog;
import com.meiwen.speedmw.gongju.StatusYouBeiBarUtil;

public class ZhuXiaoYouBeiActivity extends AppCompatActivity {

    private YouBeiRemindDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusYouBeiBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new YouBeiRemindDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new YouBeiRemindDialog.OnButtonClickListener() {
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
