package com.nfhyrhd.nfhsues.a;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nfhyrhd.nfhsues.MainApp;
import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.u.OpenUtil;
import com.nfhyrhd.nfhsues.u.StatusBarUtil;

public class AboutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_about_info);
        ImageView backImg = findViewById(R.id.back_image);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenUtil.getAppVersionName(MainApp.getContext()));
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("关于我们");
    }
}
