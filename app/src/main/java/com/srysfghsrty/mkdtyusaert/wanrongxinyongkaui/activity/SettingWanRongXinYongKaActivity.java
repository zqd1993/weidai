package com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.srysfghsrty.mkdtyusaert.ActivityCollector;
import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.mvp.XActivity;
import com.srysfghsrty.mkdtyusaert.router.Router;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.LoginWanRongXinYongKaActivity;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.StatusBarUtilWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.ToastWanRongXinYongKaUtil;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkawidget.NormalWanRongXinYongKaDialog;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkawidget.SwitchWanRongXinYongKaButton;

import butterknife.BindView;

public class SettingWanRongXinYongKaActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchWanRongXinYongKaButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalWanRongXinYongKaDialog normalWanRongXinYongKaDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilWanRongXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesUtilisWanRongXinYongKa.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchWanRongXinYongKaButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesUtilisWanRongXinYongKa.saveBoolIntoPref("push", isChecked);
                ToastWanRongXinYongKaUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalWanRongXinYongKaDialog = new NormalWanRongXinYongKaDialog(this);
            normalWanRongXinYongKaDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalWanRongXinYongKaDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalWanRongXinYongKaDialog.dismiss();
                        SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginWanRongXinYongKaActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wan_rong_xin_yong_ka_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
