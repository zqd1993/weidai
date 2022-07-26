package com.rtyhdfh.mnzdfgdsg.uuuu.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.rtyhdfh.mnzdfgdsg.GeiNiHuaApp;
import com.rtyhdfh.mnzdfgdsg.R;
import com.rtyhdfh.mnzdfgdsg.utils.GeiNiHuaStaticUtil;
import com.rtyhdfh.mnzdfgdsg.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.StatusGeiNiHuaBarUtil;
import com.rtyhdfh.mnzdfgdsg.mvp.XActivity;

public class AboutUsActivityGeiNiHua extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.version_code_tv)
    TextView version_code_tv;

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusGeiNiHuaBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilisGeiNiHua.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        version_code_tv.setText("当前版本号：v" + GeiNiHuaStaticUtil.getAppVersionName(GeiNiHuaApp.getContext()));
    }

    public static String kjgfhjfcg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double nbbzdfgz(Object o) {

        return toDouble(o, 0);
    }

    public static double sdfdszg(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long vdsfgf(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us_geinihua;
    }

    @Override
    public Object newP() {
        return null;
    }
}
