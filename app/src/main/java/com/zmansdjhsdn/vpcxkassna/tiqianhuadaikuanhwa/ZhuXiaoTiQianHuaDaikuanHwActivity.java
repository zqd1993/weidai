package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.StatusBarTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhww.RemindDialogTiQianHuaDaikuanHw;

public class ZhuXiaoTiQianHuaDaikuanHwActivity extends AppCompatActivity {

    private RemindDialogTiQianHuaDaikuanHw dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarTiQianHuaDaikuanHwUtil.setTransparent(this, false);
        if (PreferencesOpenUtilTiQianHuaDaikuanHw.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_ti_qian_hua_dai_kuan_hw_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindDialogTiQianHuaDaikuanHw(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindDialogTiQianHuaDaikuanHw.OnButtonClickListener() {
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
