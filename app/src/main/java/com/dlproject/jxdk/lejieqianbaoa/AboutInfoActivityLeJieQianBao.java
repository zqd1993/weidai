package com.dlproject.jxdk.lejieqianbaoa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dlproject.jxdk.LeJieQianBaoMainApp;
import com.dlproject.jxdk.R;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoOpenUtil;
import com.dlproject.jxdk.lejieqianbaou.PreferencesOpenUtilLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoStatusBarUtil;

public class AboutInfoActivityLeJieQianBao extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeJieQianBaoStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilLeJieQianBao.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_le_jie_qian_bao);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + LeJieQianBaoOpenUtil.getAppVersion(LeJieQianBaoMainApp.getContext()));
        textView.setText("关于我们");
    }
}
