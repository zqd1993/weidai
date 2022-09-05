package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.activityyijiekuandfwetr;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.ActivityCollector;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XActivity;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.router.Router;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.RuYiJieKuanAdgFsdfLoginActivity;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStatusBarUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfToastUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.widgetyijiekuandfwetr.RuYiJieKuanAdgFsdfNormalDialog;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.widgetyijiekuandfwetr.RuYiJieKuanAdgFsdfSwitchButton;

import butterknife.BindView;

public class SettingRuYiJieKuanAdgFsdfActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    RuYiJieKuanAdgFsdfSwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private RuYiJieKuanAdgFsdfNormalDialog ruYiJieKuanAdgFsdfNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        RuYiJieKuanAdgFsdfStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new RuYiJieKuanAdgFsdfSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                RuYiJieKuanAdgFsdfToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            ruYiJieKuanAdgFsdfNormalDialog = new RuYiJieKuanAdgFsdfNormalDialog(this);
            ruYiJieKuanAdgFsdfNormalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        ruYiJieKuanAdgFsdfNormalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        ruYiJieKuanAdgFsdfNormalDialog.dismiss();
                        RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(RuYiJieKuanAdgFsdfLoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public Object newP() {
        return null;
    }
}
