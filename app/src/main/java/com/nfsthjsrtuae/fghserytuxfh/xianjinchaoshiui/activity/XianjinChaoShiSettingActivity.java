package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.ActivityCollector;
import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.LoginXianjinChaoShiActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.StatusBarUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.ToastUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget.NormalXianjinChaoShiDialog;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget.XianjinChaoShiSwitchButton;

import butterknife.BindView;

public class XianjinChaoShiSettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    XianjinChaoShiSwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalXianjinChaoShiDialog normalXianjinChaoShiDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilXianjinChaoShi.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesXianjinChaoShiUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new XianjinChaoShiSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesXianjinChaoShiUtilis.saveBoolIntoPref("push", isChecked);
                ToastUtilXianjinChaoShi.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalXianjinChaoShiDialog = new NormalXianjinChaoShiDialog(this);
            normalXianjinChaoShiDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalXianjinChaoShiDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalXianjinChaoShiDialog.dismiss();
                        SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginXianjinChaoShiActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_xian_jin_chao_shi;
    }

    @Override
    public Object newP() {
        return null;
    }
}
