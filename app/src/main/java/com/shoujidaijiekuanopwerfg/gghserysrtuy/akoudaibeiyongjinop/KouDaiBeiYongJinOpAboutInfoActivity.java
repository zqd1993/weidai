package com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.MainKouDaiBeiYongJinOpApp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.OpenKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.StatusBarKouDaiBeiYongJinOpUtil;

public class KouDaiBeiYongJinOpAboutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKouDaiBeiYongJinOpUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKouDaiBeiYongJinOp.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_kou_dai_bei_yong_jin_op);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenKouDaiBeiYongJinOpUtil.getAppVersion(MainKouDaiBeiYongJinOpApp.getContext()));
        textView.setText("关于我们");
    }
}
