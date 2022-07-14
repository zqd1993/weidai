package com.gdzfgesry.nbfgnwaet.uiqianbao.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdzfgesry.nbfgnwaet.ActivityCollector;
import com.gdzfgesry.nbfgnwaet.R;
import com.gdzfgesry.nbfgnwaet.mvp.XActivity;
import com.gdzfgesry.nbfgnwaet.router.Router;
import com.gdzfgesry.nbfgnwaet.uiqianbao.LoginQianBaoActivity;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.QianBaoStatusQianBaoBarUtil;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.ToastQianBaoUtil;
import com.gdzfgesry.nbfgnwaet.qianbaowidget.NormalDialogQianBao;
import com.gdzfgesry.nbfgnwaet.qianbaowidget.QianBaoSwitchButton;

import butterknife.BindView;

public class SettingQianBaoActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    QianBaoSwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

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

    private String phone = "";
    private boolean isPush = false;

    private NormalDialogQianBao normalDialogQianBao;

    @Override
    public void initData(Bundle savedInstanceState) {
        QianBaoStatusQianBaoBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedQianBaoPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedQianBaoPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new QianBaoSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedQianBaoPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastQianBaoUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalDialogQianBao = new NormalDialogQianBao(this);
            normalDialogQianBao.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalDialogQianBao.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalDialogQianBao.dismiss();
                        SharedQianBaoPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginQianBaoActivity.class)
                                .launch();
                    }).show();
        });
    }

    public static String rtyhzdfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double hxrthxfgh(Object o) {

        return toDouble(o, 0);
    }

    public static double erthuxhz(Object o, int defaultValue) {
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

    public static long jzrhdfj(Object o, long defaultValue) {
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
        return R.layout.activity_qian_bao_setting;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static String mzxhxfgj(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double tghxfg(Object o) {

        return toDouble(o, 0);
    }

    public static double jthyzdr(Object o, int defaultValue) {
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

    public static long yysrhxfgj(Object o, long defaultValue) {
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
}
