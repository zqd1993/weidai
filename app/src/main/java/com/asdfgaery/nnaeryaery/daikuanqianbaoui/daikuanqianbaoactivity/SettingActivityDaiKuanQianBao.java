package com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaoactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.asdfgaery.nnaeryaery.ActivityCollector;
import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaowidget.DaiKuanQianBaoNormalDialog;
import com.asdfgaery.nnaeryaery.daikuanqianbaowidget.SwitchDaiKuanQianBaoButton;
import com.asdfgaery.nnaeryaery.mvp.XActivity;
import com.asdfgaery.nnaeryaery.router.Router;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.DaiKuanQianBaoLoginActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.ToastUtilDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.DaiKuanQianBaoStatusBarUtil;

import butterknife.BindView;

public class SettingActivityDaiKuanQianBao extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchDaiKuanQianBaoButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private DaiKuanQianBaoNormalDialog daiKuanQianBaoNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        DaiKuanQianBaoStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedDaiKuanQianBaoPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchDaiKuanQianBaoButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedDaiKuanQianBaoPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastUtilDaiKuanQianBao.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            daiKuanQianBaoNormalDialog = new DaiKuanQianBaoNormalDialog(this);
            daiKuanQianBaoNormalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        daiKuanQianBaoNormalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        daiKuanQianBaoNormalDialog.dismiss();
                        SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(DaiKuanQianBaoLoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dai_kuan_qian_bao_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
