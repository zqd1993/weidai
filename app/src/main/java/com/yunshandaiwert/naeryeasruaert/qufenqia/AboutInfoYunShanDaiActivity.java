package com.yunshandaiwert.naeryeasruaert.qufenqia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yunshandaiwert.naeryeasruaert.YunShanDaiMainApp;
import com.yunshandaiwert.naeryeasruaert.R;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiOpenUtil;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiPreferencesOpenUtil;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiStatusBarUtil;

public class AboutInfoYunShanDaiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YunShanDaiStatusBarUtil.setTransparent(this, false);
        if (YunShanDaiPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_yun_shan_dai);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + YunShanDaiOpenUtil.getAppVersion(YunShanDaiMainApp.getContext()));
        textView.setText("关于我们");
    }
}
