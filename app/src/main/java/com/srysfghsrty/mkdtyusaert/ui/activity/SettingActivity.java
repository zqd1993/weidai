package com.srysfghsrty.mkdtyusaert.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.srysfghsrty.mkdtyusaert.ActivityCollector;
import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;
import com.srysfghsrty.mkdtyusaert.router.Router;
import com.srysfghsrty.mkdtyusaert.ui.LoginActivity;
import com.srysfghsrty.mkdtyusaert.utils.SharedPreferencesUtilis;
import com.srysfghsrty.mkdtyusaert.utils.StatusBarUtil;
import com.srysfghsrty.mkdtyusaert.utils.ToastUtil;
import com.srysfghsrty.mkdtyusaert.widget.NormalDialog;
import com.srysfghsrty.mkdtyusaert.widget.SwitchButton;

import butterknife.BindView;

public class SettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalDialog normalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalDialog = new NormalDialog(this);
            normalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalDialog.dismiss();
                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
