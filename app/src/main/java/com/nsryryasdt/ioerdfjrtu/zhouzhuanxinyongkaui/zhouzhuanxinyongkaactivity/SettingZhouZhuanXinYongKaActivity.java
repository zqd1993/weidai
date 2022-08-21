package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsryryasdt.ioerdfjrtu.ActivityCollector;
import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.mvp.XActivity;
import com.nsryryasdt.ioerdfjrtu.router.Router;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.LoginZhouZhuanXinYongKaActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.StatusBarUtilZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.ToastZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkawidget.NormalZhouZhuanXinYongKaDialog;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkawidget.SwitchZhouZhuanXinYongKaButton;

import butterknife.BindView;

public class SettingZhouZhuanXinYongKaActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchZhouZhuanXinYongKaButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalZhouZhuanXinYongKaDialog normalZhouZhuanXinYongKaDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilZhouZhuanXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesUtilisZhouZhuanXinYongKa.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchZhouZhuanXinYongKaButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesUtilisZhouZhuanXinYongKa.saveBoolIntoPref("push", isChecked);
                ToastZhouZhuanXinYongKaUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalZhouZhuanXinYongKaDialog = new NormalZhouZhuanXinYongKaDialog(this);
            normalZhouZhuanXinYongKaDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalZhouZhuanXinYongKaDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalZhouZhuanXinYongKaDialog.dismiss();
                        SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginZhouZhuanXinYongKaActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }
}
