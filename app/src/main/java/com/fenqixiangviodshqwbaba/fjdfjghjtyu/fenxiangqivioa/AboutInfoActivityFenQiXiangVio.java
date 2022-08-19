package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.MainFenQiXiangVioApp;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.FenQiXiangVioOpenUtil;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.StatusBarUtilFenQiXiangVio;

public class AboutInfoActivityFenQiXiangVio extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtilFenQiXiangVio.setTransparent(this, false);
        if (PreferencesOpenUtilFenQiXiangVio.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_fen_xiang_qi_vio);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + FenQiXiangVioOpenUtil.getAppVersion(MainFenQiXiangVioApp.getContext()));
        textView.setText("关于我们");
    }
}
