package com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.activitydkmiaoxia;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.StatusDKMiaoXiaOpBarUtil;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.ToastUtilDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XActivity;

public class DKMiaoXiaOpFeedBackActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView  titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.feedback_et)
    EditText feedbackEt;
    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.rotate_loading)
    RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    View loadingFl;

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
        titleTv.setText("意见反馈");
        commitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(feedbackEt.getText().toString().trim())){
                ToastUtilDKMiaoXiaOp.showShort("请输入提交内容");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingFl.setVisibility(View.GONE);
                    rotateLoading.stop();
                    ToastUtilDKMiaoXiaOp.showShort("提交成功");
                    finish();
                }
            }, 2000);
        });
    }

    public static String hjkfghjc(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ewrdfz(Object o) {

        return toDouble(o, 0);
    }

    public static double nmxfgzdg(Object o, int defaultValue) {
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

    public static long bnfgdhtsy(Object o, long defaultValue) {
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
        return R.layout.activity_dk_miao_xia_op_new_feedback;
    }

    @Override
    public Object newP() {
        return null;
    }
}
