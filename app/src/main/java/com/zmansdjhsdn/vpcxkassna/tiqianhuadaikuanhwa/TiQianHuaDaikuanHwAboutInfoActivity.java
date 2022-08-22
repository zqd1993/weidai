package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zmansdjhsdn.vpcxkassna.MainTiQianHuaDaikuanHwApp;
import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.OpenTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.StatusBarTiQianHuaDaikuanHwUtil;

public class TiQianHuaDaikuanHwAboutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarTiQianHuaDaikuanHwUtil.setTransparent(this, false);
        if (PreferencesOpenUtilTiQianHuaDaikuanHw.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_ti_qian_hua_dai_kuan_hw);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenTiQianHuaDaikuanHwUtil.getAppVersion(MainTiQianHuaDaikuanHwApp.getContext()));
        textView.setText("关于我们");
    }
}
