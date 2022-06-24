package com.meiwen.speedmw.yemian;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.meiwen.speedmw.MainYouBeiApp;
import com.meiwen.speedmw.R;
import com.meiwen.speedmw.gongju.OpenYouBeiUtil;
import com.meiwen.speedmw.gongju.StatusYouBeiBarUtil;

public class AboutInfoYouBeiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusYouBeiBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_about_info);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenYouBeiUtil.getAppVersionName(MainYouBeiApp.getContext()));
        textView.setText("关于我们");
    }
}
