package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.StatusBarZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgweidgt.RemindZhaoCaiAdfmDialog;

public class ZhuXiaoXiangFenQiActivity extends AppCompatActivity {

    private RemindZhaoCaiAdfmDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarZhaoCaiAdfmUtil.setTransparent(this, false);
        if (PreferencesOpenUtilZhaoCaiAdfm.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_zhuxiao_zhao_cai_endfi_weng);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindZhaoCaiAdfmDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindZhaoCaiAdfmDialog.OnButtonClickListener() {
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
