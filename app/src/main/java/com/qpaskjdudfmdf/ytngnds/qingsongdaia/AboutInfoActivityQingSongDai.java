package com.qpaskjdudfmdf.ytngnds.qingsongdaia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qpaskjdudfmdf.ytngnds.MainAppQingSongDai;
import com.qpaskjdudfmdf.ytngnds.R;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.OpenQingSongDaiUtil;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.PreferencesOpenUtilQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.QingSongDaiStatusBarUtil;

public class AboutInfoActivityQingSongDai extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QingSongDaiStatusBarUtil.setTransparent(this, false);
        if (PreferencesOpenUtilQingSongDai.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_qing_song_dai);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenQingSongDaiUtil.getAppVersion(MainAppQingSongDai.getContext()));
        textView.setText("关于我们");
    }
}
