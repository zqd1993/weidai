package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jieuacnisdoert.naweodfigety.ActivityCollector;
import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.mvp.XActivity;
import com.jieuacnisdoert.naweodfigety.router.Router;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.JieKuanZhiJiaLoginActivity;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStatusBarUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaToastUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiawidget.JieKuanZhiJiaNormalDialog;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiawidget.JieKuanZhiJiaSwitchButton;

import butterknife.BindView;

public class SettingJieKuanZhiJiaActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    JieKuanZhiJiaSwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private JieKuanZhiJiaNormalDialog jieKuanZhiJiaNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        JieKuanZhiJiaStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = JieKuanZhiJiaSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new JieKuanZhiJiaSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                JieKuanZhiJiaSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                JieKuanZhiJiaToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            jieKuanZhiJiaNormalDialog = new JieKuanZhiJiaNormalDialog(this);
            jieKuanZhiJiaNormalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        jieKuanZhiJiaNormalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        jieKuanZhiJiaNormalDialog.dismiss();
                        JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(JieKuanZhiJiaLoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_jie_kuan_zhi_jia;
    }

    @Override
    public Object newP() {
        return null;
    }
}
