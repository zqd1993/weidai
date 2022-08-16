package com.pwekqha.itmgs.koudaibeiyongjina;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pwekqha.itmgs.MainAppKouDaiBeiYongJin;
import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjinu.OpenUtilKouDaiBeiYongJin;
import com.pwekqha.itmgs.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;
import com.pwekqha.itmgs.koudaibeiyongjinu.StatusBarKouDaiBeiYongJinUtil;

public class AboutInfoActivityKouDaiBeiYongJin extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKouDaiBeiYongJinUtil.setTransparent(this, false);
        if (KouDaiBeiYongJinPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_kou_dai_bei_yong_jin);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenUtilKouDaiBeiYongJin.getAppVersion(MainAppKouDaiBeiYongJin.getContext()));
        textView.setText("关于我们");
    }
}
