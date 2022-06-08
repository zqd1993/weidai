package com.xvhyrt.ghjtyu.act;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.uti.ZhuangTaiLanUtil;

public class WoMenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZhuangTaiLanUtil.setTransparent(this, false);
        setContentView(R.layout.activity_guanyuwomen);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("关于我们");
    }
}
