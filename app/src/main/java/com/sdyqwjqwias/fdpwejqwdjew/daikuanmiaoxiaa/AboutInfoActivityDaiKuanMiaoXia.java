package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sdyqwjqwias.fdpwejqwdjew.MainDaiKuanMiaoXiaApp;
import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.OpenDaiKuanMiaoXiaUtil;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.PreferencesOpenUtilDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.StatusBarDaiKuanMiaoXiaUtil;

public class AboutInfoActivityDaiKuanMiaoXia extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarDaiKuanMiaoXiaUtil.setTransparent(this, false);
        if (PreferencesOpenUtilDaiKuanMiaoXia.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_dai_kuan_miao_xia);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenDaiKuanMiaoXiaUtil.getAppVersion(MainDaiKuanMiaoXiaApp.getContext()));
        textView.setText("关于我们");
    }
}
