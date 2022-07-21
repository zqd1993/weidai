package com.fdhsdjqqhds.ppfdzabsdvd.qufenqia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fdhsdjqqhds.ppfdzabsdvd.QuFenQiMainApp;
import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.OpenUtilQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiStatusBarUtil;

public class AboutInfoActivityQuFenQi extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuFenQiStatusBarUtil.setTransparent(this, false);
        if (PreferencesQuFenQiOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_qu_fen_qi);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenUtilQuFenQi.getAppVersion(QuFenQiMainApp.getContext()));
        textView.setText("关于我们");
    }
}
