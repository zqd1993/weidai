package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.StatusBarUtilFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviow.FenQiXiangVioRemindDialog;

public class FenQiXiangVioZhuXiaoActivity extends AppCompatActivity {

    private FenQiXiangVioRemindDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilFenQiXiangVio.setTransparent(this, false);
        if (PreferencesOpenUtilFenQiXiangVio.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_fen_xiang_qi_vio);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new FenQiXiangVioRemindDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new FenQiXiangVioRemindDialog.OnButtonClickListener() {
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
