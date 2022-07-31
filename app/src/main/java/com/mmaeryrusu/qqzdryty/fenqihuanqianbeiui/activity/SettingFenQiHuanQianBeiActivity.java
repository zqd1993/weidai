package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmaeryrusu.qqzdryty.ActivityCollector;
import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.mvp.XActivity;
import com.mmaeryrusu.qqzdryty.router.Router;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.LoginFenQiHuanQianBeiActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.StatusFenQiHuanQianBeiBarUtil;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.NormalFenQiHuanQianBeiDialog;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.SwitchFenQiHuanQianBeiButton;

import butterknife.BindView;

public class SettingFenQiHuanQianBeiActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchFenQiHuanQianBeiButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalFenQiHuanQianBeiDialog normalFenQiHuanQianBeiDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusFenQiHuanQianBeiBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = FenQiHuanQianBeiSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchFenQiHuanQianBeiButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                FenQiHuanQianBeiSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastUtilFenQiHuanQianBei.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalFenQiHuanQianBeiDialog = new NormalFenQiHuanQianBeiDialog(this);
            normalFenQiHuanQianBeiDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalFenQiHuanQianBeiDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalFenQiHuanQianBeiDialog.dismiss();
                        FenQiHuanQianBeiSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginFenQiHuanQianBeiActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_qi_huan_qian_bei_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
