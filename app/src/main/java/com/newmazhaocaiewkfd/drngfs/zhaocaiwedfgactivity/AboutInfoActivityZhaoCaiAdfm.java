package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newmazhaocaiewkfd.drngfs.MainAppZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.OpenZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.StatusBarZhaoCaiAdfmUtil;

public class AboutInfoActivityZhaoCaiAdfm extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarZhaoCaiAdfmUtil.setTransparent(this, false);
        if (PreferencesOpenUtilZhaoCaiAdfm.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_about_info_zhao_cai_endfi_weng);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenZhaoCaiAdfmUtil.getAppVersion(MainAppZhaoCaiAdfm.getContext()));
        textView.setText("关于我们");
    }
}
