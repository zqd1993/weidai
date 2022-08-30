package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingjijiefasdfbbdr.dfgeryxfg.ActivityCollector;
import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.LoginYjjdFqjqActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.ToastYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.router.Router;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqStatusBarUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqwidget.NormalYjjdFqjqDialog;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqwidget.SwitchButtonYjjdFqjq;

import butterknife.BindView;

public class YjjdFqjqSettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchButtonYjjdFqjq switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalYjjdFqjqDialog normalYjjdFqjqDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        YjjdFqjqStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = YjjdFqjqSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchButtonYjjdFqjq.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                YjjdFqjqSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastYjjdFqjqUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalYjjdFqjqDialog = new NormalYjjdFqjqDialog(this);
            normalYjjdFqjqDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalYjjdFqjqDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalYjjdFqjqDialog.dismiss();
                        YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginYjjdFqjqActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yjjdfqjq_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
