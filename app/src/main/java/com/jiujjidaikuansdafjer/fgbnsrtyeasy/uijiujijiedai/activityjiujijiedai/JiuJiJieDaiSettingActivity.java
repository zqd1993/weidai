package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.activityjiujijiedai;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.ActivityCollector;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.JiuJiJieDaiLoginActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.router.Router;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiStatusBarUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiToastUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.widgetjiujijiedai.JiuJiJieDaiNormalDialog;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.widgetjiujijiedai.JiuJiJieDaiSwitchButton;

import butterknife.BindView;

public class JiuJiJieDaiSettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    JiuJiJieDaiSwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private JiuJiJieDaiNormalDialog jiuJiJieDaiNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieDaiStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = JiuJiJieDaiSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new JiuJiJieDaiSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                JiuJiJieDaiSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                JiuJiJieDaiToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            jiuJiJieDaiNormalDialog = new JiuJiJieDaiNormalDialog(this);
            jiuJiJieDaiNormalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        jiuJiJieDaiNormalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        jiuJiJieDaiNormalDialog.dismiss();
                        JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(JiuJiJieDaiLoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_jiu_ji_jie_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
