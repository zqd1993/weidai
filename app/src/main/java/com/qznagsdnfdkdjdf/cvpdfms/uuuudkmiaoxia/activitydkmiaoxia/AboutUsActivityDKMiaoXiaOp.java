package com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.activitydkmiaoxia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.qznagsdnfdkdjdf.cvpdfms.DKMiaoXiaOpApp;
import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.DKMiaoXiaOpStaticUtil;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.StatusDKMiaoXiaOpBarUtil;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XActivity;

public class AboutUsActivityDKMiaoXiaOp extends XActivity {

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
        if (SharedPreferencesUtilisDKMiaoXiaOp.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        StatusDKMiaoXiaOpBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("关于");
        version_code_tv.setText(DKMiaoXiaOpStaticUtil.getAppVersionName(DKMiaoXiaOpApp.getContext()));
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
        return R.layout.activity_about_us_dk_miao_xia_op_new;
    }

    @Override
    public Object newP() {
        return null;
    }
}
