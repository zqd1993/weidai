package com.fdhsdjqqhds.ppfdzabsdvd.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fdhsdjqqhds.ppfdzabsdvd.MainApp;
import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.OpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.PreferencesOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.StatusBarUtil;

public class AboutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenUtil.getAppVersion(MainApp.getContext()));
        textView.setText("关于我们");
    }
}
