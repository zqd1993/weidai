package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaiactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiejijiekuansdafjer.zdfgbaeryazer.ActivityCollector;
import com.jiejijiekuansdafjer.zdfgbaeryazer.R;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.JiuJiJieDaiLoginActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.router.Router;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiStatusBarUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiToastUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiwidget.JiuJiJieDaiNormalDialog;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiwidget.JiuJiJieDaiSwitchButton;

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
