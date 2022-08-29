package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dshqbzansdk.vpcvlsdksdhayjtop.R;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.PreferencesOpenUtilQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.QingSongDaiStatusBarUtil;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiw.RemindQingSongDaiDialog;

public class QingSongDaiZhuXiaoActivity extends AppCompatActivity {

    private RemindQingSongDaiDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QingSongDaiStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilQingSongDai.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_qing_song_dai_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindQingSongDaiDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindQingSongDaiDialog.OnButtonClickListener() {
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
