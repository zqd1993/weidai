package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dqlsdjdhwnew.fdhqwenhwnew.MainMangGuoHWNewApp;
import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MangGuoHWNewOpenUtil;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.StatusBarUtilMangGuoHWNew;

public class AboutInfoActivityMangGuoHWNew extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilMangGuoHWNew.setTransparent(this, false);
        if (PreferencesOpenUtilMangGuoHWNew.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_mang_guo_hw_new);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + MangGuoHWNewOpenUtil.getAppVersion(MainMangGuoHWNewApp.getContext()));
        textView.setText("关于我们");
    }
}
