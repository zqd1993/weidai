package com.xvhyrt.ghjtyu.a;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xvhyrt.ghjtyu.MainApp;
import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.u.OpenUtil;
import com.xvhyrt.ghjtyu.u.StatusBarUtil;

public class AboutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_about_info);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenUtil.getAppVersion(MainApp.getContext()));
        textView.setText("关于我们");
    }
}
