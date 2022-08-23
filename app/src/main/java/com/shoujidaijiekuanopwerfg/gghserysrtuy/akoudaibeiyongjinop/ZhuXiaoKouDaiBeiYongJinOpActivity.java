package com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.StatusBarKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.wkoudaibeiyongjinop.RemindDialogKouDaiBeiYongJinOp;

public class ZhuXiaoKouDaiBeiYongJinOpActivity extends AppCompatActivity {

    private RemindDialogKouDaiBeiYongJinOp dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKouDaiBeiYongJinOpUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKouDaiBeiYongJinOp.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_kou_dai_bei_yong_jin_op_zhuxiao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindDialogKouDaiBeiYongJinOp(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindDialogKouDaiBeiYongJinOp.OnButtonClickListener() {
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
