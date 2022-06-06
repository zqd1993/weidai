package com.rtvfbgfh.yuiyjghn.a;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rtvfbgfh.yuiyjghn.R;
import com.rtvfbgfh.yuiyjghn.u.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoAboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_renren_info);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("关于我们");
    }

    public static String getAllTimeNoSecondWithDot(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getAllTimeWithDot(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getAllTimeNoSecond(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

}
