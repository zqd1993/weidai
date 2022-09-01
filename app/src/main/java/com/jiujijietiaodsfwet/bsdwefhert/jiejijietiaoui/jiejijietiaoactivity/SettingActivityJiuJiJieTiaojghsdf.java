package com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui.jiejijietiaoactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiujijietiaodsfwet.bsdwefhert.ActivityCollector;
import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfToastUtil;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;
import com.jiujijietiaodsfwet.bsdwefhert.mvp.XActivity;
import com.jiujijietiaodsfwet.bsdwefhert.router.Router;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoui.JiuJiJieTiaojghsdfLoginActivity;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfStatusBarUtil;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaowidget.JiuJiJieTiaojghsdfNormalDialog;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaowidget.SwitchJiuJiJieTiaojghsdfButton;

import butterknife.BindView;

public class SettingActivityJiuJiJieTiaojghsdf extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchJiuJiJieTiaojghsdfButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private JiuJiJieTiaojghsdfNormalDialog jiuJiJieTiaojghsdfNormalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiuJiJieTiaojghsdfStatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchJiuJiJieTiaojghsdfButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                JiuJiJieTiaojghsdfToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            jiuJiJieTiaojghsdfNormalDialog = new JiuJiJieTiaojghsdfNormalDialog(this);
            jiuJiJieTiaojghsdfNormalDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        jiuJiJieTiaojghsdfNormalDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        jiuJiJieTiaojghsdfNormalDialog.dismiss();
                        SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(JiuJiJieTiaojghsdfLoginActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jiu_ji_jie_tiao_boss_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
