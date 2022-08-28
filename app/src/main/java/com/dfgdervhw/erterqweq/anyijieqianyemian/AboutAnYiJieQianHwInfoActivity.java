package com.dfgdervhw.erterqweq.anyijieqianyemian;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dfgdervhw.erterqweq.WAnYiJieQianHwApp;
import com.dfgdervhw.erterqweq.R;
import com.dfgdervhw.erterqweq.anyijieqiangongju.OpenAnYiJieQianHwUtil;
import com.dfgdervhw.erterqweq.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;
import com.dfgdervhw.erterqweq.anyijieqiangongju.StatusBarAnYiJieQianHwUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AboutAnYiJieQianHwInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarAnYiJieQianHwUtil.setTransparent(this, false);
        if (PreferencesAnYiJieQianHwOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.j__an_yi_jie_qian_activity_about_info);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenAnYiJieQianHwUtil.getAppVersion(WAnYiJieQianHwApp.getContext()));
        textView.setText("关于我们");
    }

    public static String formatDoublePointTwo(double money) {
        //保持小数点后两位
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(money);
    }

    public static String formatTimeYMD(String time) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "日期获取失败";
        }
    }
}
