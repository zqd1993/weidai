package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.MainKuaiJieJieKuanNewVoApp;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.OpenKuaiJieJieKuanNewVoUtil;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.PreferencesOpenUtilKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.StatusBarKuaiJieJieKuanNewVoUtil;

public class KuaiJieJieKuanNewVoAboutInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarKuaiJieJieKuanNewVoUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKuaiJieJieKuanNewVo.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_kuai_jie_jie_kuan_new_op);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenKuaiJieJieKuanNewVoUtil.getAppVersion(MainKuaiJieJieKuanNewVoApp.getContext()));
        textView.setText("关于我们");
    }
}
