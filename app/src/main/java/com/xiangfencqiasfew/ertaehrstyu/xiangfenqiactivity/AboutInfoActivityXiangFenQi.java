package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiangfencqiasfew.ertaehrstyu.MainAppXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.R;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.OpenXiangFenQiUtil;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.PreferencesOpenUtilXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.StatusBarXiangFenQiUtil;

public class AboutInfoActivityXiangFenQi extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarXiangFenQiUtil.setTransparent(this, false);
        if (PreferencesOpenUtilXiangFenQi.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_xiang_fen_qi);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenXiangFenQiUtil.getAppVersion(MainAppXiangFenQi.getContext()));
        textView.setText("关于我们");
    }
}
