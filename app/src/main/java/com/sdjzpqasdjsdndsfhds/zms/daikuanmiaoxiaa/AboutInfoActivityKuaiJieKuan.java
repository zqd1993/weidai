package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sdjzpqasdjsdndsfhds.zms.MainKuaiJieKuanApp;
import com.sdjzpqasdjsdndsfhds.zms.R;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.OpenKuaiJieKuanUtil;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.StatusBarKuaiJieKuanUtil;

public class AboutInfoActivityKuaiJieKuan extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKuaiJieKuanUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKuaiJieKuan.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_kuai_jie_kuan);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenKuaiJieKuanUtil.getAppVersion(MainKuaiJieKuanApp.getContext()));
        textView.setText("关于我们");
    }
}