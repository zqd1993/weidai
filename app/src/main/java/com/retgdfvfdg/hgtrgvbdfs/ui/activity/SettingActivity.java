package com.retgdfvfdg.hgtrgvbdfs.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.retgdfvfdg.hgtrgvbdfs.R;
import com.retgdfvfdg.hgtrgvbdfs.mvp.XActivity;
import com.retgdfvfdg.hgtrgvbdfs.utils.SharedPreferencesUtilis;
import com.retgdfvfdg.hgtrgvbdfs.utils.StatusBarUtil;
import com.retgdfvfdg.hgtrgvbdfs.utils.ToastUtil;
import com.retgdfvfdg.hgtrgvbdfs.widget.SwitchButton;

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

    private String phone = "";
    private boolean isPush = false;

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
