package com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.activitydkmiaoxia;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.StatusDKMiaoXiaOpBarUtil;
import com.qznagsdnfdkdjdf.cvpdfms.wwwwdkmiaoxia.NormalDialogDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XActivity;

public class CancellationDKMiaoXiaOpAccountActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private NormalDialogDKMiaoXiaOp normalDialogDKMiaoXiaOp;

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
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            normalDialogDKMiaoXiaOp = new NormalDialogDKMiaoXiaOp(this);
            normalDialogDKMiaoXiaOp.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> normalDialogDKMiaoXiaOp.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        normalDialogDKMiaoXiaOp.dismiss();
//                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
//                        Router.newIntent(CancellationAccountActivity.this)
//                                .to(LoginActivity.class)
//                                .launch();
                        finish();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dk_miao_xia_op_new_cancellation_account;
    }

    public static String mgfxfgh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double erdsgz(Object o) {

        return toDouble(o, 0);
    }

    public static double hxfthfdh(Object o, int defaultValue) {
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

    public static long bvxfgh(Object o, long defaultValue) {
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
    public Object newP() {
        return null;
    }

    @Override
    protected void onDestroy() {
        if (normalDialogDKMiaoXiaOp != null) {
            normalDialogDKMiaoXiaOp.dismiss();
            normalDialogDKMiaoXiaOp = null;
        }
        super.onDestroy();
    }
}
