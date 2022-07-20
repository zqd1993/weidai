package com.suishijie.ndjufgjhdrty.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.suishijie.ndjufgjhdrty.ActivityCollector;
import com.suishijie.ndjufgjhdrty.R;
import com.suishijie.ndjufgjhdrty.mvp.XActivity;
import com.suishijie.ndjufgjhdrty.router.Router;
import com.suishijie.ndjufgjhdrty.ui.LoginActivity;
import com.suishijie.ndjufgjhdrty.utils.SharedPreferencesUtilis;
import com.suishijie.ndjufgjhdrty.utils.StatusBarUtil;
import com.suishijie.ndjufgjhdrty.utils.ToastUtil;
import com.suishijie.ndjufgjhdrty.widget.NormalDialog;
import com.suishijie.ndjufgjhdrty.widget.SwitchButton;

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
