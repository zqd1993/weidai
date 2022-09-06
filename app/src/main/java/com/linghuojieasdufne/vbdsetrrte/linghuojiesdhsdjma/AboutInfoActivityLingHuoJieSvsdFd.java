package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.linghuojieasdufne.vbdsetrrte.LingHuoJieSvsdFdMainApp;
import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdOpenUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdStatusBarUtil;

public class AboutInfoActivityLingHuoJieSvsdFd extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LingHuoJieSvsdFdStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLingHuoJieSvsdFd.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_ling_huo_jie_djs_urng);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + LingHuoJieSvsdFdOpenUtil.getAppVersion(LingHuoJieSvsdFdMainApp.getContext()));
        textView.setText("关于我们");
    }
}
