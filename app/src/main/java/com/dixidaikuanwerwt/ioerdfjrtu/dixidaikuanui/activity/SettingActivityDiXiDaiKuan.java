package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dixidaikuanwerwt.ioerdfjrtu.ActivityCollector;
import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.router.Router;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.DiXiDaiKuanLoginActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanToastUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanStatusBarUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanwidget.DiXiDaiKuanNormalDialog;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanwidget.SwitchDiXiDaiKuanButton;

import butterknife.BindView;

public class SettingActivityDiXiDaiKuan extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchDiXiDaiKuanButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private DiXiDaiKuanNormalDialog diXiDaiKuanNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        DiXiDaiKuanStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedDiXiDaiKuanPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchDiXiDaiKuanButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedDiXiDaiKuanPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                DiXiDaiKuanToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            diXiDaiKuanNormalDialog = new DiXiDaiKuanNormalDialog(this);
            diXiDaiKuanNormalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        diXiDaiKuanNormalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        diXiDaiKuanNormalDialog.dismiss();
                        SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(DiXiDaiKuanLoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_di_xi_dai_kuan_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
